package me.lanzhi.bluestarbot.internal.message;

import me.lanzhi.bluestarbot.api.Internal;
import me.lanzhi.bluestarbot.api.contact.Contact;
import me.lanzhi.bluestarbot.api.message.ForwardMessage;
import me.lanzhi.bluestarbot.internal.Mapping;
import net.mamoe.mirai.message.data.ForwardMessageBuilder;
import net.mamoe.mirai.message.data.RawForwardMessage;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static me.lanzhi.bluestarbot.api.message.ForwardMessage.Display.DEFAULT;

@Internal
public final class ForwardMessageImpl implements MessageImpl, ForwardMessage
{
    private final Display display;
    private final List<ForwardMessage.Node> nodes;
    private final Contact contact;

    public ForwardMessageImpl(List<ForwardMessage.Node> nodes,Contact contact,Display display)
    {
        this.nodes=nodes;
        this.contact=contact;
        this.display=display;
    }

    public ForwardMessageImpl(net.mamoe.mirai.message.data.ForwardMessage msg)
    {
        List<ForwardMessage.Node> list=new ArrayList<>();
        msg.getNodeList()
           .forEach(node->list.add(new Node(node.getSenderId(),
                                            node.getSenderName(),
                                            Mapping.map(node.getMessageChain()),
                                            new Date(node.getTime()*1000L))));
        this.nodes=list;
        this.contact=null;
        this.display=new Display()
        {
            @Override
            public String title(ForwardMessage message)
            {
                return msg.getTitle();
            }

            @Override
            public String brief(ForwardMessage message)
            {
                return msg.getBrief();
            }

            @Override
            public List<String> preview(ForwardMessage message)
            {
                return msg.getPreview();
            }

            @Override
            public String summary(ForwardMessage message)
            {
                return msg.getSummary();
            }
        };
    }

    public ForwardMessageImpl(List<net.mamoe.mirai.message.data.ForwardMessage.Node> nodes)
    {
        List<ForwardMessage.Node> list=new ArrayList<>();
        nodes.forEach(node->list.add(new Node(node.getSenderId(),
                                              node.getSenderName(),
                                              Mapping.map(node.getMessageChain()),
                                              new Date(node.getTime()*1000L))));
        this.nodes=list;
        this.contact=null;
        this.display=DEFAULT;
    }

    @Override
    public net.mamoe.mirai.message.data.ForwardMessage toMirai()
    {
        ForwardMessageBuilder builder=new ForwardMessageBuilder(contact.getToContact());
        for (ForwardMessage.Node node: nodes)
        {
            builder.add(node.id(),node.name(),((MessageChainImpl) node.messages()).toMirai());
        }
        builder.setDisplayStrategy(new Display0(display));
        return builder.build();
    }

    @NotNull
    @Override
    public Iterator<ForwardMessage.Node> iterator()
    {
        return nodes.iterator();
    }

    @Override
    public int getSize()
    {
        return nodes.size();
    }

    @Override
    public Display getDisplay()
    {
        return display;
    }

    private final static class Display0 implements net.mamoe.mirai.message.data.ForwardMessage.DisplayStrategy
    {
        private final Display display;

        private Display0(Display display)
        {
            this.display=display;
        }

        @NotNull
        @Override
        public String generateBrief(@NotNull RawForwardMessage forward)
        {
            return display.brief(new ForwardMessageImpl(forward.getNodeList()));
        }

        @NotNull
        @Override
        public List<String> generatePreview(@NotNull RawForwardMessage forward)
        {
            return display.preview(new ForwardMessageImpl(forward.getNodeList()));
        }

        @NotNull
        @Override
        public String generateSummary(@NotNull RawForwardMessage forward)
        {
            return display.summary(new ForwardMessageImpl(forward.getNodeList()));
        }

        @NotNull
        @Override
        public String generateTitle(@NotNull RawForwardMessage forward)
        {
            return display.title(new ForwardMessageImpl(forward.getNodeList()));
        }
    }
}
