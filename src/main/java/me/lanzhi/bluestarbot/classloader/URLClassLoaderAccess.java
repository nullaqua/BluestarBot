/*
 * 我懒到直接复制粘贴了
 *     -- DreamVoid
 *
 * This file is part of helper, licensed under the MIT License.
 *
 *  Copyright (c) lucko (Luck) <luck@lucko.me>
 *  Copyright (c) contributors
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package me.lanzhi.bluestarbot.classloader;

import me.lanzhi.api.reflect.MethodAccessor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collection;

public abstract class URLClassLoaderAccess
{
    private final URLClassLoader classLoader;
    protected URLClassLoaderAccess(URLClassLoader classLoader)
    {
        this.classLoader=classLoader;
    }
    static URLClassLoaderAccess create(final URLClassLoader classLoader)
    {
        return new URLClassLoaderAccess(classLoader)
        {
            private final MethodAccessor methodAccessor=MethodAccessor.getDeclaredMethod(URLClassLoader.class,"addURL",URL.class);
            @Override
            public void addURL(URL url)
            {
                try
                {
                    methodAccessor.invoke(classLoader,url);
                }
                catch (Throwable e)
                {
                    throw new RuntimeException(e);
                }
            }
        };

    }

    public abstract void addURL(URL url);

    private static class Reflection extends URLClassLoaderAccess
    {
        private static final Method ADD_URL_METHOD;

        static
        {
            Method addUrlMethod;
            try
            {
                addUrlMethod=URLClassLoader.class.getDeclaredMethod("addURL",URL.class);
                addUrlMethod.setAccessible(true);
            }
            catch (Exception e)
            {
                addUrlMethod=null;
            }
            ADD_URL_METHOD=addUrlMethod;
        }

        Reflection(URLClassLoader classLoader)
        {
            super(classLoader);
        }

        private static boolean isSupported()
        {
            return ADD_URL_METHOD!=null;
        }

        @Override
        public void addURL(URL url)
        {
            try
            {
                ADD_URL_METHOD.invoke(super.classLoader,url);
            }
            catch (ReflectiveOperationException e)
            {
                throw new RuntimeException(e);
            }
        }
    }
    private static class Unsafe extends URLClassLoaderAccess
    {
        private static final sun.misc.Unsafe UNSAFE;

        static
        {
            sun.misc.Unsafe unsafe;
            try
            {
                Field unsafeField=sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
                unsafeField.setAccessible(true);
                unsafe=(sun.misc.Unsafe) unsafeField.get(null);
            }
            catch (Throwable t)
            {
                unsafe=null;
            }
            UNSAFE=unsafe;
        }

        private final Collection<URL> unopenedURLs;
        private final Collection<URL> pathURLs;

        @SuppressWarnings("unchecked")
        Unsafe(URLClassLoader classLoader)
        {
            super(classLoader);

            Collection<URL> unopenedURLs;
            Collection<URL> pathURLs;
            try
            {
                Object ucp=fetchField(URLClassLoader.class,classLoader,"ucp");
                unopenedURLs=(Collection<URL>) fetchField(ucp.getClass(),ucp,"unopenedUrls");
                pathURLs=(Collection<URL>) fetchField(ucp.getClass(),ucp,"path");
            }
            catch (Throwable e)
            {
                unopenedURLs=null;
                pathURLs=null;
            }
            this.unopenedURLs=unopenedURLs;
            this.pathURLs=pathURLs;
        }

        private static boolean isSupported()
        {
            return UNSAFE!=null;
        }

        private static Object fetchField(final Class<?> clazz,final Object object,final String name) throws NoSuchFieldException
        {
            Field field=clazz.getDeclaredField(name);
            long offset=UNSAFE.objectFieldOffset(field);
            return UNSAFE.getObject(object,offset);
        }

        @Override
        public void addURL(URL url)
        {
            this.unopenedURLs.add(url);
            this.pathURLs.add(url);
        }
    }

    private static class Noop extends URLClassLoaderAccess
    {
        private static final Noop INSTANCE=new Noop();

        private Noop()
        {
            super(null);
        }

        @Override
        public void addURL(URL url)
        {
            throw new UnsupportedOperationException();
        }
    }

}