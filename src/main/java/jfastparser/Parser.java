//Copyright Hugh Perkins 2012, hughperkins -at- gmail
//
//This Source Code Form is subject to the terms of the Mozilla Public License, 
//v. 2.0. If a copy of the MPL was not distributed with this file, You can 
//obtain one at http://mozilla.org/MPL/2.0/.

package jfastparser;

public final class Parser {
    final String line;
    final char[] linearray;
    int pos;
    final int len;

    public Parser(String line) {
        this.line = line;
        this.linearray = line.toCharArray();
        len = linearray.length;
    }

    public final int eatInt() {
        return eatInt(' ');
    }

    public final int eatInt(char delimiter) {
        int newpos = pos;
        int value = 0;
        boolean neg = false;
        if (pos >= len) {
            throw new RuntimeException("pos at end of string");
        }
        if (linearray[pos] == '-') {
            neg = true;
            newpos++;
        }
        if (linearray[pos] == '+') {
            pos++;
            newpos++;
        }
        char thischar = 0;
        while (newpos < len && (thischar = linearray[newpos]) != delimiter && thischar >= '0' && thischar <= '9') {
            value = value * 10 + (int) (linearray[newpos] - '0');
            newpos++;
        }
        if (pos == newpos) {
            throw new RuntimeException(
                    "expected int but found [" + linearray[pos] + "] in [" + StringHelper.mid(line, pos, 50) + "]");
        }
        pos = newpos;
        return neg ? -value : value;
    }

    public final double eatDouble() {
        double value = 0;
        boolean negative = false;
        boolean afterpoint = false;
        double divider = 1;
        char thischar = 0;
        int oldpos = pos;
        while (pos < len && (thischar = linearray[pos]) != ' ' && thischar != 'e' && thischar != '\t') {
            if (thischar == '-') {
                negative = true;
            } else if (thischar == '.') {
                afterpoint = true;
            } else {
                int thisdigit = thischar - '0';
                value = value * 10 + thisdigit;
                if (afterpoint) {
                    divider *= 10;
                }
            }
            pos++;
        }
        if (thischar == 'e') {
            pos++;
            boolean exponentnegative = false;
            int exponent = 0;
            while (pos < len && (thischar = linearray[pos]) != ' ' && thischar != '\t') {
                if (thischar == '-') {
                    exponentnegative = true;
                } else if (thischar != '+') {
                    exponent = exponent * 10 + (thischar - '0');
                }
                pos++;
            }
            if (exponentnegative) {
                exponent = -exponent;
            }
            value *= Math.pow(10, exponent);
        }
        if (negative) {
            value = -value;
        }
        value /= divider;
        if (pos == oldpos) {
            throw new RuntimeException(
                    "expected double but found [" + linearray[pos] + "] in [" + StringHelper.mid(line, pos, 50) + "]");
        }
        return value;
    }

    public final void eatWhitespace() {
        while (pos < len && (linearray[pos] == ' ' || linearray[pos] == '\t')) {
            pos++;
        }
    }

    public final void eatChar(char target) {
        if (linearray[pos] != target) {
            throw new RuntimeException(
                    "expected " + target + " but got " + linearray[pos] + " in [" + linearray.toString() + "]");
        }
        pos++;
    }

    public final boolean more() {
        return pos < len;
    }
}
