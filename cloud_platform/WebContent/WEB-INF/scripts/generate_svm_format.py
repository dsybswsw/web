#! /usr/bin/env python
# -*- coding: utf-8 -*-

__author__="Shiwei Wu <dsybswsw@gmail.com>"
__date__ ="$Mar 31, 2013"

import sys

SPLITER = '|||'

def generate_svm_format(input_file, output_file):
    in_file = open(input_file, 'r')
    out_file = open(output_file, 'w')
    for line in in_file.readlines():
        str_list = line.split(SPLITER)
        for i in range(0, len(str_list)):
            str = str_list[i]
            if len(str.strip()) == 0:
                continue
            if i == 0:
                str = str[2:len(str)]
                out_file.write(str + '\n')
            out_file.write(str + '\n')
    out_file.close()

if __name__ == '__main__':
    args = sys.argv
    if len(args) != 3:
        print "generate_svm_format input output"
    generate_svm_format(args[1], args[2])
