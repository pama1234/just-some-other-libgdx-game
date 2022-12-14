/****************************************************************************
 ** Copyright (c) 2013-2018 Mazatech S.r.l. All rights reserved.
 ** 
 ** Redistribution and use in source and binary forms, with or without modification, are
 * permitted (subject to the limitations in the disclaimer below) provided that the following
 * conditions are met:
 ** 
 ** - Redistributions of source code must retain the above copyright notice, this list of
 * conditions and the following disclaimer.
 ** 
 ** - Redistributions in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other materials provided
 * with the distribution.
 ** 
 ** - Neither the name of Mazatech S.r.l. nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written
 * permission.
 ** 
 ** NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS LICENSE. THIS
 * SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY
 * AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER
 ** OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 ** OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 ** 
 ** For any information, please contact info@mazatech.com
 ** 
 ****************************************************************************/
package com.mazatech.svgt;

// Java
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;

// libGDX
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public final class SVGAssets{
    private static boolean copyStreams(InputStream input,
        OutputStream output) throws IOException {
        boolean ok=false;
        if((input!=null)&&(output!=null)) {
            try {
                byte[] buffer=new byte[4096];
                while(true) {
                    int length=input.read(buffer);
                    if(length==-1) {
                        break;
                    }
                    output.write(buffer,0,length);
                }
                ok=true;
            }catch(IOException e) {
                throw new RuntimeException(e);
            }finally {
                // close streams
                input.close();
                output.close();
            }
        }
        return ok;
    }
    private static boolean loadNativeDesktop(String srcPath,String outputFileName) {
        boolean ok=false;
        InputStream input=AmanithSVG.class.getResourceAsStream(srcPath);
        if(input!=null) {
            File outputFile=new File(outputFileName);
            // ensure the existence of destination directory
            outputFile.getParentFile().mkdirs();
            try {
                FileOutputStream output=new FileOutputStream(outputFile);
                // copy the native library to a safe (executable) location
                if(copyStreams(input,output)) {
                    // perform the real load of dynamic code
                    System.load(outputFile.getAbsolutePath());
                    // we have finished
                    ok=true;
                }
            }catch(FileNotFoundException e) {
                System.out.println("SVGManager: File "+outputFile.getAbsolutePath()+" not found");
            }catch(UnsatisfiedLinkError e) {
                System.out.println("SVGManager: Native code library failed to load "+outputFile.getAbsolutePath()+", the file does not exist.");
            }catch(SecurityException e) {
                System.out.println("SVGManager: Native code library failed to load "+outputFile.getAbsolutePath()+", the security manager doesn't allow loading of the specified dynamic library.");
            }catch(IOException e) {
                System.out.println("SVGManager: Error extracting file "+srcPath+" to "+outputFile.getAbsolutePath());
            }
        }
        return ok;
    }
    private static boolean loadSharedLibraryDesktop(String jarSubDir,String nativeFileName) {
        boolean ok=true;
        String srcPath="/"+jarSubDir+"/"+nativeFileName;
        // try temp directory with username in path     
        if(!loadNativeDesktop(srcPath,System.getProperty("java.io.tmpdir")+File.separator+"AmanithSVG"+File.separator+System.getProperty("user.name")+File.separator+nativeFileName)) {
            // try user home
            File file=new File(System.getProperty("user.home")+File.separator+".AmanithSVG"+File.separator,nativeFileName);
            if(!loadNativeDesktop(srcPath,file.getAbsolutePath())) {
                // try relative directory
                file=new File(".temp"+File.separator,nativeFileName);
                ok=loadNativeDesktop(srcPath,file.getAbsolutePath());
            }
        }
        return ok;
    }
    private static void loadAmanithSVG() {
        boolean isIOS="iOS".equals(System.getProperty("moe.platform.name"));
        // in case of iOS, things have been linked statically to the executable
        if(!isIOS) {
            String os=System.getProperty("os.name").toLowerCase();
            boolean isWindows=os.contains("windows");
            boolean isLinux=os.contains("linux");
            boolean isMac=os.contains("mac");
            boolean isAndroid=false;
            String arch=System.getProperty("os.arch").toLowerCase();
            boolean is64Bit=arch.equals("amd64")||arch.equals("x86_64")||arch.equals("aarch64");
            boolean isArm=arch.startsWith("arm")||arch.equals("aarch64");
            String vm=System.getProperty("java.vm.name");
            String runtime=System.getProperty("java.runtime.name");
            // detect Android
            if(((vm!=null)&&vm.toLowerCase().contains("dalvik"))||((runtime!=null)&&runtime.toLowerCase().contains("android runtime"))) {
                isAndroid=true;
                isWindows=false;
                isLinux=false;
                isMac=false;
                is64Bit=false;
            }
            if(isAndroid) {
                System.loadLibrary("AmanithSVG");
                System.loadLibrary("AmanithSVGJNI");
            }else {
                java.lang.String path="";
                java.lang.String libName="";
                java.lang.String jniName="";
                if(isWindows) {
                    // Windows desktop
                    path=(is64Bit)?"win/x86_64":"win/x86";
                    libName="libAmanithSVG.dll";
                    jniName="libAmanithSVGJNI.dll";
                }else if(isLinux) {
                    if(isArm) {
                        // Linux ARM
                        path=(is64Bit)?"linux/arm64-v8a":"linux/arm-v7a";
                    }else {
                        // Linux desktop
                        path=(is64Bit)?"linux/x86_64":"linux/x86";
                    }
                    libName="libAmanithSVG.so";
                    jniName="libAmanithSVGJNI.so";
                }else if(isMac) {
                    // MacOS X universal binary
                    path="macosx/ub";
                    libName="libAmanithSVG.dylib";
                    jniName="libAmanithSVGJNI.dylib";
                }
                // load AmanithSVG native library
                loadSharedLibraryDesktop(path,libName);
                // load JNI binding for the native library; NB: this second library must be loaded as second, because it depends on the first
                loadSharedLibraryDesktop(path,jniName);
            }
        }
    }
    private static void initAmanithSVG() {
        AmanithSVG.svgtInit(getScreenResolutionWidth(),getScreenResolutionHeight(),getScreenDpi());
    }
    private static void destroyAmanithSVG() {
        AmanithSVG.svgtDone();
    }
    // NB: you MUST implement this functionality according to the underlying graphics system; this implementation is for libGDX backend only
    public static int getScreenResolutionWidth() {
        return Gdx.graphics.getBackBufferWidth();
    }
    // NB: you MUST implement this functionality according to the underlying graphics system; this implementation is for libGDX backend only
    public static int getScreenResolutionHeight() {
        return Gdx.graphics.getBackBufferHeight();
    }
    // NB: you MUST implement this functionality according to the underlying graphics system; this implementation is for libGDX backend only
    public static float getScreenDpi() {
        return Gdx.graphics.getPpiX();
    }
    /*
     * Initialize the AmanithSVG library as well as the JNI wrapper mechanism.
     * 
     * NB: before using any other method of this class (e.g. createSurface, createDocument,
     * createPacker), it is MANDATORY to call this funtion as a first thing to do.
     */
    public static void init() {
        if(!_isInitialized) {
            // load AmanithSVG native libraries
            loadAmanithSVG();
            // initialize AmanithSVG library
            initAmanithSVG();
            _isInitialized=true;
        }
    }
    public static void dispose() {
        if(_isInitialized) {
            destroyAmanithSVG();
        }
    }
    // Create a drawing surface, specifying its dimensions in pixels.
    public static SVGSurface createSurface(int width,int height) {
        SVGSurface result=null;
        // ensure AmanithSVG library initialization
        init();
        if((width<=0)||(height<=0)) {
            throw new IllegalArgumentException("Invalid (negative or zero) surface dimensions");
        }else {
            // create and keep track of the AmanithSVG surface handle
            SVGTHandle surface=AmanithSVG.svgtSurfaceCreate(width,height);
            if(surface!=null) {
                result=new SVGSurface(surface.getNativeHandle());
            }else {
                throw new IllegalStateException("Native surface cannot be created; system is out of memory");
            }
        }
        return result;
    }
    // Create and load an SVG document, specifying the whole xml string.
    public static SVGDocument createDocument(String xmlText) {
        SVGDocument result=null;
        // ensure AmanithSVG library initialization
        init();
        if((xmlText==null)||(xmlText.length()==0)) {
            throw new IllegalArgumentException("xml == null or empty");
        }else {
            // create AmanithSVG document handle
            SVGTHandle document=AmanithSVG.svgtDocCreate(xmlText);
            if(document==null) {
                throw new IllegalStateException("Error building SVGDocument from xml");
            }else {
                result=new SVGDocument(document.getNativeHandle());
            }
        }
        return result;
    }
    // Create and load an SVG document, specifying a file handle; NB: this method is libGDX specific.
    public static SVGDocument createDocument(FileHandle file) {
        SVGDocument result=null;
        if(file==null) {
            throw new IllegalArgumentException("file == null");
        }else {
            result=createDocument(file.readString());
        }
        return result;
    }
    public static SVGPacker createPacker(float scale,int maxTexturesDimension,int border,boolean pow2Textures) {
        // ensure AmanithSVG library initialization
        init();
        return new SVGPacker(scale,maxTexturesDimension,border,pow2Textures);
    }
    private static boolean _isInitialized=false;
}
