//Copyright Hugh Perkins 2011, 2012, hughperkins -at- gmail
//
//This Source Code Form is subject to the terms of the Mozilla Public License, 
//v. 2.0. If a copy of the MPL was not distributed with this file, You can 
//obtain one at http://mozilla.org/MPL/2.0/.

package jfastparser;

public final class StringHelper {
    public static final String truncate(String in, int length) {
        if (in.length() <= length) {
            return in;
        }
        return in.substring(0, length);
    }

    public static final String truncateLeft(String in, int length) {
        if (in.length() <= length) {
            return in;
        }
        return in.substring(in.length() - length);
    }

    public static final String formatDouble(double value) {
        return truncate("" + value, 4);
    }

    public static final String mid(String in, int pos, int maxlength) {
        if (pos + maxlength > in.length()) {
            return in.substring(pos, in.length());
        } else {
            return in.substring(pos, maxlength + pos);
        }
    }
}
