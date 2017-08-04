package org.sxiong.yarpc.common.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by sxiong on 7/27/17.
 */
public class ConfigManager {
    private static final Logger logger = LoggerFactory.getLogger(ConfigManager.class);

    private static final String EXTERNAL_CONFIG_FILE = "/data/yarpc/env";
    private static final String CLASSPATH_CONFIG_FILE = "/com/sxiong/rpc/yarpc.properties";

    private static volatile ConfigManager instance;

    private Properties config;

    public static ConfigManager getInstance(){
        if (instance == null){
            synchronized (ConfigManager.class){
                if (instance == null){
                    instance = new ConfigManager();
                }
            }
        }

        return instance;
    }

    private ConfigManager(){
        logger.debug("start to load config...");

        config = new Properties();

        //load config from EXTERNAL_CONFIG_FILE
        InputStream externFileInputStream = null;
        try{
            externFileInputStream = new FileInputStream(EXTERNAL_CONFIG_FILE);
            config.load(externFileInputStream);

            logger.debug("load config from "+EXTERNAL_CONFIG_FILE);
        } catch (Exception e) {
            logger.error("Error when loading config file form " + EXTERNAL_CONFIG_FILE + " : "+e.getMessage(),e);
        } finally {
            if (externFileInputStream!=null){
                try{
                    externFileInputStream.close();
                } catch (IOException e) {
                    logger.error("Error when closing config file:" + EXTERNAL_CONFIG_FILE +" : "+e.getMessage(),e);
                }
            }
        }

        //load config from classpath file CLASSPATH_CONFIG_FILE
        InputStream classpathConfigFileInputStream = ConfigManager.class.getResourceAsStream(CLASSPATH_CONFIG_FILE);
        if (classpathConfigFileInputStream!=null){
            try {
                config.load(classpathConfigFileInputStream);
                logger.debug("load config from "+CLASSPATH_CONFIG_FILE);
            } catch (IOException e) {
                logger.error("Error when loading classpath config file form " + CLASSPATH_CONFIG_FILE + " : "+e.getMessage(),e);
            }finally {
                if (classpathConfigFileInputStream!=null){
                    try{
                        classpathConfigFileInputStream.close();
                    } catch (IOException e) {
                        logger.error("Error when closing classpath config file:" + CLASSPATH_CONFIG_FILE +" : "+e.getMessage(),e);
                    }
                }
            }
        }else{
            logger.error("failed to load zk config file :" + CLASSPATH_CONFIG_FILE);
        }
    }

    public String getProperty(String key){
        return this.config.getProperty(key);
    }

    public static void main(String[] args) {
        System.out.println(getInstance().config);
        System.out.println(System.getProperty("java.class.path"));
    }
}
