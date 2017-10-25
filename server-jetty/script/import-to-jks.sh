#!/bin/bash

alias=$1
storepass=$2
keystore=$3
file=$4

keytool -import -v -trustcacerts -alias $alias -file $file -keystore $keystore -keypass $storepass -storepass $storepass