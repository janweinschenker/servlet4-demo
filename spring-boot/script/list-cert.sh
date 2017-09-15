#!/bin/bash
storepass=$1
keystore=$2

keytool -list -v -keystore $keystore -storepass $storepass