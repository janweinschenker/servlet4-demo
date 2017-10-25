#!/bin/bash
alias=$1
storepass=$2
keystore=$3
$JAVA_HOME/bin/keytool -delete -noprompt -alias $alias -storepass $storepass -keystore $keystore
