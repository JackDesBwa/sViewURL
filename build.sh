#!/bin/sh
[ _$ANDROID_HOME == _ ] && echo "Error: env variable ANDROID_HOME should be set." && exit 1
gradle assembleRelease
cp build/outputs/apk/release/sViewURL-release.apk sViewURL.apk
