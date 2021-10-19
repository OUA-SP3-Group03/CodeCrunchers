package io.codecrunchers.facades;

import io.codecrunchers.core.Kernel;

public class Config {

    public final Kernel kernel;

    public Config(Kernel kernel){
        this.kernel = kernel;
    }

    //*** GET INTERFACE WIDTH ****\\
    public int interfaceWidth(){
        return this.kernel.getConfig().interfaceWidth;
    }

    //*** GET INTERFACE HEIGHT ****\\
    public int interfaceHeight(){
        return this.kernel.getConfig().interfaceHeight;
    }

    //*** GET INTERFACE TITLE ****\\
    public String interfaceTitle(){
        return this.kernel.getConfig().title;
    }

    //**** GET TARGET FPS  ****\\
    public int targetFPS(){
        return this.kernel.getConfig().targetFPS;
    }

    //**** GET TARGET TPS ****\\
    public int targetTPS(){
        return this.kernel.getConfig().targetTPS;
    }

    //**** GET SHOW FPS ****\\
    public boolean showFPS(){
        return this.kernel.getConfig().showFPS;
    }

    //**** GET API URL ****\\
    public String apiUrl(){
        return this.kernel.getConfig().apiUrl;
    }

    //**** GET TEXTURE WIDTH ****\\
    public int textureWidth(){
        return this.kernel.getConfig().textureWidth;
    }

    //**** GET TEXTURE HEIGHT ****\\
    public int textureHeight(){
        return this.kernel.getConfig().textureHeight;
    }
    //**** GET TEXTURE MAP WIDTH ****\\
    public int textureMapWidth(){
        return this.kernel.getConfig().textureMapWidth;
    }
    //**** GET TEXTURE MAP HEIGHT ****\\
    public int textureMapHeight(){
        return this.kernel.getConfig().textureMapHeight;
    }
    //**** GET TEXTURE PATH ****\\
    public String texturePath(){
        return this.kernel.getConfig().texturePath;
    }
}