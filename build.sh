#!/bin/bash
set -e

echo "🚀 LUMINA NATIVE CORE COMPILER STARTING..."
export BUILD_DIR=~/lumina_build
export PROJECT_DIR=$(pwd)
rm -rf $BUILD_DIR/gen $BUILD_DIR/obj $BUILD_DIR/apk
mkdir -p $BUILD_DIR/gen $BUILD_DIR/obj $BUILD_DIR/apk

# 1. Compile and Link Android Resource Layouts
echo "📦 [1/5] Linking layout structures and manifest maps..."
aapt package -f -m \
    -J $BUILD_DIR/gen \
    -M $PROJECT_DIR/app/AndroidManifest.xml \
    -I $BUILD_DIR/sdk/android.jar

# 2. Compile Native Java Code Structures
echo "⚙️ [2/5] Initializing openjdk source compilers..."
find . -name "*.java" > $BUILD_DIR/sources.txt
javac -target 1.8 -source 1.8 \
    -bootclasspath $BUILD_DIR/sdk/android.jar \
    -d $BUILD_DIR/obj \
    @$BUILD_DIR/sources.txt

# 3. Translate Class Architectures into proper Android Dex Binary
echo "🔀 [3/5] Compiling machine byte-code into Dalvik Dex formats..."
if command -v d8 &> /dev/null; then
    d8 --output $BUILD_DIR/apk $BUILD_DIR/obj/com/lumina/*.class
elif command -v dx &> /dev/null; then
    dx --dex --output=$BUILD_DIR/apk/classes.dex $BUILD_DIR/obj
else
    echo "❌ Error: Neither d8 nor dx tools were found. Please run 'pkg install android-tools'."
    exit 1
fi

# 4. Assemble the Initial Distribution Frame
echo "🎒 [4/5] Packaging binary frames into final archive structures..."
aapt package -f \
    -M $PROJECT_DIR/app/AndroidManifest.xml \
    -I $BUILD_DIR/sdk/android.jar \
    -F $BUILD_DIR/apk/lumina-unsigned.apk

cd $BUILD_DIR/apk
zip -uj lumina-unsigned.apk classes.dex
cd $PROJECT_DIR

# 5. Cryptographically Sign the Application for Installation
echo "🔏 [5/5] Cryptographically generating keys and signing APK..."
if [ ! -f "$BUILD_DIR/lumina.keystore" ]; then
    keytool -genkeypair -validity 3650 -keyalg RSA -keysize 2048 \
        -keystore $BUILD_DIR/lumina.keystore \
        -storepass luminapass -keypass luminapass \
        -alias luminakey -dname "CN=Lumina, O=OS, C=US"
fi

SIGNER_CMD="apksigner"
if command -v android-apksigner &> /dev/null; then
    SIGNER_CMD="android-apksigner"
fi

$SIGNER_CMD sign --ks $BUILD_DIR/lumina.keystore \
    --ks-pass pass:luminapass \
    --out $PROJECT_DIR/lumina.apk \
    $BUILD_DIR/apk/lumina-unsigned.apk

echo "🏁 SUCCESS! LUMINA OPERATING SYSTEM COMPILED."
echo "📲 Target output located at: $PROJECT_DIR/lumina.apk"
