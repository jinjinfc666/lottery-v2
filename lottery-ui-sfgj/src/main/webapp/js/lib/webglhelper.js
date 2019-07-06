/**********************************************************
IEWebGL support routines
You can copy, use, modify, distribute this file.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS

"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT 
LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
**********************************************************/

var WebGLHelper = {

    'GetGLContext': function (cnv, attributes) {
        var ctxNames = ["webgl", "experimental-webgl"];
        var glCtx = null;
        try {
            for (var i = 0; i < ctxNames.length && glCtx == null; ++i)
                glCtx = cnv.getContext(ctxNames[i], attributes);
        }
        catch (e) { }
        return glCtx;
    },

    'CreateNativeCanvas': function (element, id, replace) {
        var cnv = document.createElement("canvas");
        if (replace) {
            if (element.attributes.width) cnv.width = element.attributes.width.value;
            if (element.attributes.height) cnv.height = element.attributes.height.value;
            if (element.attributes.style) cnv.style.cssText = element.attributes.style.value;
            element.parentNode.replaceChild(cnv, element);
        }
        else {
            element.appendChild(cnv);
        }

        cnv.innerHTML = "Your browser does not support &lt;canvas&gt; tag.";
        cnv.id = id;
        return cnv;
    },

    'CreatePluginCanvas': function (element, id, replace) {
        var obj = document.createElement("object");

        if (replace) {
            if (element.attributes.width) obj.width = element.attributes.width.value;
            if (element.attributes.height) obj.height = element.attributes.height.value;
            if (element.attributes.style) obj.style.cssText = element.attributes.style.value;
            element.parentNode.replaceChild(obj, element);
        }
        else {
            element.appendChild(obj);
        }

        var altMessage = 'To get WebGL support, please <a href="http://iewebgl.com/download/latest/">download</a> and install IEWebGL plugin and refresh the page.';
        if (obj.innerHTML) {
            obj.innerHTML = altMessage;
        }
        else { /* IE8 workaround */
            obj.altHtml = altMessage;
        }
        
        obj.id = id;
        obj.type = "application/x-webgl";
        return obj;
    },

    'CreateGLCanvas': function (el, id, replace) {
        if (navigator.userAgent.indexOf("MSIE") >= 0) {
            var usePlugin;
            try {
                usePlugin = WebGLRenderingContext.hasOwnProperty('iewebgl');
            } catch (e) {
                usePlugin = true;
            }

            if (usePlugin) {
                return WebGLHelper.CreatePluginCanvas(el, id, replace);
            }
            else {
                return WebGLHelper.CreateNativeCanvas(el, id, replace);
            }
        }
        else {
            return WebGLHelper.CreateNativeCanvas(el, id, replace);
        }
    },

    'CreateGLContext': function (element, id, replace, attributes) {

        var cnv = WebGLHelper.CreateGLCanvas(element, id, replace);
        var gl = WebGLHelper.GetGLContext(cnv, attributes);

        return gl;
    },

    'CreateGLCanvasInline': function (id) {
        var placeHolder = document.getElementById("WebGLCanvasCreationScript");
        WebGLHelper.CreateGLCanvas(placeHolder, id, true);
    }

}