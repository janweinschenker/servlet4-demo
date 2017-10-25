#!/bin/bash
alias=$1
storepass=$2
keystore=$3
$JAVA_HOME/bin/keytool -genkey -alias $alias -keyalg RSA -keypass $storepass -storepass  $storepass -keystore $3
