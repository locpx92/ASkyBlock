/*
 * Adapted from the Wizardry License
 *
 * Copyright (c) 2016-2018 larryTheCoder and contributors
 *
 * Permission is hereby granted to any persons and/or organizations
 * using this software to copy, modify, merge, publish, and distribute it.
 * Said persons and/or organizations are not allowed to use the software or
 * any derivatives of the work for commercial use or any other means to generate
 * income, nor are they allowed to claim this software as their own.
 *
 * The persons and/or organizations are also disallowed from sub-licensing
 * and/or trademarking this software without explicit permission from larryTheCoder.
 *
 * Any persons and/or organizations using this software must disclose their
 * source code and have it publicly available, include this license,
 * provide sufficient credit to the original authors of the project (IE: larryTheCoder),
 * as well as provide a link to the original project.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,FITNESS FOR A PARTICULAR
 * PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

/*
 * $Id: JSONObject.java,v 1.1 2006/04/15 14:10:48 platform Exp $
 * Created on 2006-4-10
 */
package org.json.simple;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * A JSON object. Key value pairs are unordered. JSONObject supports java.util.Map interface.
 *
 * @author FangYidong<fangyidong       @       yahoo.com.cn>
 */
public class JSONObject extends HashMap implements Map, JSONAware, JSONStreamAware {

    private static final long serialVersionUID = -503443796854799292L;


    public JSONObject() {
        super();
    }

    /**
     * Allows creation of a JSONObject from a Map. After that, both the
     * generated JSONObject and the Map can be modified independently.
     *
     * @param map
     */
    public JSONObject(Map map) {
        super(map);
    }


    /**
     * Encode a map into JSON text and write it to out.
     * If this map is also a JSONAware or JSONStreamAware, JSONAware or JSONStreamAware specific behaviours will be ignored at this top level.
     *
     * @param map
     * @param out
     * @see org.json.simple.JSONValue#writeJSONString(Object, Writer)
     */
    public static void writeJSONString(Map map, Writer out) throws IOException {
        if (map == null) {
            out.write("null");
            return;
        }

        boolean first = true;
        Iterator iter = map.entrySet().iterator();

        out.write('{');
        while (iter.hasNext()) {
            if (first)
                first = false;
            else
                out.write(',');
            Map.Entry entry = (Map.Entry) iter.next();
            out.write('\"');
            out.write(escape(String.valueOf(entry.getKey())));
            out.write('\"');
            out.write(':');
            JSONValue.writeJSONString(entry.getValue(), out);
        }
        out.write('}');
    }

    /**
     * Convert a map to JSON text. The result is a JSON object.
     * If this map is also a JSONAware, JSONAware specific behaviours will be omitted at this top level.
     *
     * @param map
     * @return JSON text, or "null" if map is null.
     * @see org.json.simple.JSONValue#toJSONString(Object)
     */
    public static String toJSONString(Map map) {
        if (map == null)
            return "null";

        StringBuffer sb = new StringBuffer();
        boolean first = true;
        Iterator iter = map.entrySet().iterator();

        sb.append('{');
        while (iter.hasNext()) {
            if (first)
                first = false;
            else
                sb.append(',');

            Map.Entry entry = (Map.Entry) iter.next();
            toJSONString(String.valueOf(entry.getKey()), entry.getValue(), sb);
        }
        sb.append('}');
        return sb.toString();
    }

    private static String toJSONString(String key, Object value, StringBuffer sb) {
        sb.append('\"');
        if (key == null)
            sb.append("null");
        else
            JSONValue.escape(key, sb);
        sb.append('\"').append(':');

        sb.append(JSONValue.toJSONString(value));

        return sb.toString();
    }

    public static String toString(String key, Object value) {
        StringBuffer sb = new StringBuffer();
        toJSONString(key, value, sb);
        return sb.toString();
    }

    /**
     * Escape quotes, \, /, \r, \n, \b, \f, \t and other control characters (U+0000 through U+001F).
     * It's the same as JSONValue.escape() only for compatibility here.
     *
     * @param s
     * @return
     * @see org.json.simple.JSONValue#escape(String)
     */
    public static String escape(String s) {
        return JSONValue.escape(s);
    }

    @Override
    public void writeJSONString(Writer out) throws IOException {
        writeJSONString(this, out);
    }

    @Override
    public String toJSONString() {
        return toJSONString(this);
    }

    @Override
    public String toString() {
        return toJSONString();
    }
}
