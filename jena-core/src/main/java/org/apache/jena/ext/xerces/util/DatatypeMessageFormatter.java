/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.jena.ext.xerces.util;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * <p>Used to format JAXP 1.3 Datatype API error messages using a specified locale.</p>
 *
 * @author  Neeraj Bajaj, Sun Microsystems
 * @version $Id: DatatypeMessageFormatter.java 813087 2009-09-09 19:35:27Z mrglavas $
 */
public class DatatypeMessageFormatter {

    private static final String BASE_NAME = "org.apache.jena.ext.xerces.impl.msg.DatatypeMessages";

    /**
     * Formats a message with the specified arguments using the given
     * locale information.
     *
     * @param locale    The locale of the message.
     * @param key       The message key.
     * @param arguments The message replacement text arguments. The order
     *                  of the arguments must match that of the placeholders
     *                  in the actual message.
     *
     * @return          the formatted message.
     *
     * @throws MissingResourceException Thrown if the message with the
     *                                  specified key cannot be found.
     */
    public static String formatMessage(Locale locale,
        String key, Object[] arguments)
        throws MissingResourceException {

        if (locale == null) {
            locale = Locale.getDefault();
        }
        final ResourceBundle resourceBundle =
            ResourceBundle.getBundle(BASE_NAME, locale);

        // format message
        String msg;
        try {
            msg = resourceBundle.getString(key);
            if (arguments != null) {
                try {
                    msg = java.text.MessageFormat.format(msg, arguments);
                }
                catch (Exception e) {
                    msg = resourceBundle.getString("FormatFailed");
                    msg += " " + resourceBundle.getString(key);
                }
            }
        }

        // error
        catch (MissingResourceException e) {
            msg = resourceBundle.getString("BadMessageKey");
            throw new MissingResourceException(key, msg, key);
        }

        // msg cannot be null
        return msg;
    }
}
