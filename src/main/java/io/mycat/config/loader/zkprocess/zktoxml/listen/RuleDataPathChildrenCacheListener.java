package io.mycat.config.loader.zkprocess.zktoxml.listen;

import com.google.common.io.Files;
import io.mycat.config.model.SystemConfig;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;

import java.io.File;
import java.io.IOException;

/**
 * Created by magicdoom on 2016/10/27.
 */
public class RuleDataPathChildrenCacheListener implements PathChildrenCacheListener {
    @Override public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
        ChildData data = event.getData();
        switch (event.getType()) {

            case CHILD_ADDED:

                add(data.getPath().substring(data.getPath().lastIndexOf("/")+1),event.getData().getData()) ;
                break;
            case CHILD_REMOVED:
                delete(data.getPath().substring(data.getPath().lastIndexOf("/")+1),event.getData().getData()); ;
                break;
            case CHILD_UPDATED:
                add(data.getPath().substring(data.getPath().lastIndexOf("/")+1),event.getData().getData()) ;
                break;
            default:
                break;
        }
    }

    private void add(String name,byte[] data) throws IOException {
        File file = new File(
                SystemConfig.getHomePath() + File.separator + "conf" + File.separator + "ruledata",
                name);
        Files.write(data,file);
    }

    private void delete(String name,byte[] data) throws IOException {
        File file = new File(
                SystemConfig.getHomePath() + File.separator + "conf" + File.separator + "ruledata",
                name);
        if(file.exists())
         file.delete();
    }

}
