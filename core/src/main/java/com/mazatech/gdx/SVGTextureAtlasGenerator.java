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
package com.mazatech.gdx;

// Java
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

// libGDX
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Disposable;

// AmanithSVG
import com.mazatech.svgt.SVGTError;
import com.mazatech.svgt.SVGAssets;
import com.mazatech.svgt.SVGColor;
import com.mazatech.svgt.SVGDocument;
import com.mazatech.svgt.SVGPacker;

public class SVGTextureAtlasGenerator implements Disposable{
    public class SVGTextureAtlasPackingException extends Exception{
        public SVGTextureAtlasPackingException() {
            super();
        }
        public SVGTextureAtlasPackingException(String message) {
            super(message);
        }
    }
    public class SVGAtlasGeneratorInput{
        public SVGAtlasGeneratorInput(FileHandle file,boolean explodeGroups,float scale) {
            _file=file;
            _explodeGroups=explodeGroups;
            _scale=scale;
        }
        public FileHandle getFile() {
            return _file;
        }
        public boolean getExplodeGroups() {
            return _explodeGroups;
        }
        public float getScale() {
            return _scale;
        }
        private FileHandle _file;
        private boolean _explodeGroups;
        private float _scale;
    }
    public SVGTextureAtlasGenerator(float scale,int maxTexturesDimension,int border,boolean pow2Textures,boolean dilateEdgesFix,SVGColor clearColor) {
        // check arguments
        if(scale<=0) {
            throw new IllegalArgumentException("scale <= 0");
        }
        if(maxTexturesDimension<=0) {
            throw new IllegalArgumentException("maxTexturesDimension <= 0");
        }
        if(border<0) {
            throw new IllegalArgumentException("border < 0");
        }
        if(clearColor==null) {
            throw new IllegalArgumentException("clearColor == null");
        }
        _inputAssetsMap=new HashMap<FileHandle,SVGDocument>();
        _inputAssetsList=new ArrayList<SVGAtlasGeneratorInput>();
        _scale=scale;
        _maxTexturesDimension=maxTexturesDimension;
        _border=border;
        _pow2Textures=pow2Textures;
        _dilateEdgesFix=dilateEdgesFix;
        _clearColor=clearColor;
        fixMaxDimension();
        fixBorder();
    }
    public float getScale() {
        return _scale;
    }
    public void setScale(float scale) {
        if(scale<=0) {
            throw new IllegalArgumentException("scale <= 0");
        }else {
            _scale=scale;
        }
    }
    public int getMaxTexturesDimension() {
        return _maxTexturesDimension;
    }
    public void setMaxTexturesDimension(int maxTexturesDimension) {
        if(maxTexturesDimension<=0) {
            throw new IllegalArgumentException("maxTexturesDimension <= 0");
        }else {
            _maxTexturesDimension=maxTexturesDimension;
            fixMaxDimension();
            fixBorder();
        }
    }
    public int getBorder() {
        return _border;
    }
    public void setBorder(int border) {
        if(border<0) {
            throw new IllegalArgumentException("border < 0");
        }else {
            _border=border;
            fixBorder();
        }
    }
    public boolean getPow2Textures() {
        return _pow2Textures;
    }
    public void setPow2Textures(boolean pow2Textures) {
        _pow2Textures=pow2Textures;
        fixMaxDimension();
        fixBorder();
    }
    public SVGColor getClearColor() {
        return new SVGColor(_clearColor);
    }
    public void setClearColor(SVGColor clearColor) {
        if(clearColor==null) {
            throw new IllegalArgumentException("clearColor == null");
        }else {
            _clearColor.set(clearColor);
        }
    }
    private void fixMaxDimension() {
        if(_maxTexturesDimension==0) {
            _maxTexturesDimension=1;
        }else {
            // check power-of-two option
            if(_pow2Textures&&(!SVGTextureUtils.isPow2(_maxTexturesDimension))) {
                // set maxTexturesDimension to the smallest power of two value greater (or equal) to it
                _maxTexturesDimension=SVGTextureUtils.pow2Get(_maxTexturesDimension);
            }
        }
    }
    private void fixBorder() {
        // border must allow a packable region of at least one pixel
        int maxAllowedBorder=((_maxTexturesDimension&1)!=0)?(_maxTexturesDimension/2):((_maxTexturesDimension-1)/2);
        if(_border>maxAllowedBorder) {
            _border=maxAllowedBorder;
        }
    }
    public boolean add(FileHandle file,boolean explodeGroups,float scale) {
        // we can't add the same SVG file multiple times
        if(_inputAssetsMap.containsKey(file)) {
            return false;
        }
        _inputAssetsMap.put(file,null);
        _inputAssetsList.add(new SVGAtlasGeneratorInput(file,explodeGroups,scale));
        return true;
    }
    private void loadDocuments() {
        // create and load SVG documents
        for(Map.Entry<FileHandle,SVGDocument> entry:_inputAssetsMap.entrySet()) {
            // get the associated SVG document
            SVGDocument doc=entry.getValue();
            // if not yet loaded, create it
            if(doc==null) {
                doc=SVGAssets.createDocument(entry.getKey());
                entry.setValue(doc);
            }
        }
    }
    private SVGPacker.SVGPackedBin[] performPacking() throws SVGTextureAtlasPackingException {
        SVGTError err;
        int[] info=new int[2];
        SVGPacker packer=SVGAssets.createPacker(_scale,_maxTexturesDimension,_border,_pow2Textures);
        // start a new packing process
        if((err=packer.begin())!=SVGTError.None) {
            return null;
        }
        // loop over input SVG file, adding each of them to the packing process
        for(SVGAtlasGeneratorInput inputAsset:_inputAssetsList) {
            SVGDocument doc=_inputAssetsMap.get(inputAsset.getFile());
            if(doc!=null) {
                if((err=packer.add(doc,inputAsset.getExplodeGroups(),inputAsset.getScale(),info))!=SVGTError.None) {
                    // abort the packing process
                    packer.end(false);
                    return null;
                }
                // info[0] = number of collected bounding boxes
                // info[1] = the actual number of packed bounding boxes
                if(info[1]<info[0]) {
                    // abort the packing process
                    packer.end(false);
                    throw new SVGTextureAtlasPackingException("Specified maximum texture dimensions (in conjunction with specified scale factor), do not allow the packing of all SVG elements");
                }
            }
        }
        return packer.end(true);
    }
    // NB: this method MUST be called from the OpenGL thread, because it creates textures
    public SVGTextureAtlas generateAtlas() throws SVGTextureAtlasPackingException {
        SVGPacker.SVGPackedBin[] packerResult;
        // ensure that SVG documents are loaded
        loadDocuments();
        // run the packer and generate the atlas (i.e. textures and regions)
        return ((packerResult=performPacking())!=null)?new SVGTextureAtlas(packerResult,_dilateEdgesFix,_clearColor):null;
    }
    @Override
    public void dispose() {
        // destroy SVG documents
        for(SVGDocument doc:_inputAssetsMap.values()) {
            if(doc!=null) {
                doc.dispose();
            }
        }
        _inputAssetsList.clear();
        _inputAssetsList=null;
        _inputAssetsMap.clear();
        _inputAssetsMap=null;
    }
    private float _scale=1.0f;
    private int _maxTexturesDimension=1024;
    private int _border=1;
    private boolean _pow2Textures=true;
    private boolean _dilateEdgesFix=true;
    private SVGColor _clearColor=SVGColor.Clear;
    // map each SVG file to the relative SVGDocument
    private Map<FileHandle,SVGDocument> _inputAssetsMap=null;
    private List<SVGAtlasGeneratorInput> _inputAssetsList=null;
}