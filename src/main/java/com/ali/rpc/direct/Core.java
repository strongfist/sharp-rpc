package com.ali.rpc.direct;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ali.rpc.Rpc;
import com.ali.rpc.exception.RPCException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
public class Core {

    //    private static final String shapConfigFilePath;
    private Map<String, Rpc> rpcMap;

    //default that is not a pwd
    private static Map<String, String> variables;


    private static Map<String, List<Response>> responses;


    private static Map<String, List<Request>> requests;


    static {
        var rpcConfigFilename = System.getenv("SHARP_CONFIG");
        if (StringUtils.isEmpty(rpcConfigFilename))
            throw new RPCException(" env DB_CONFIG_LOCATION must not empty! ");

        if (rpcConfigFilename.split(",").length > 1) {
            initForMultipleFIle(rpcConfigFilename);
        }
        configureRequestTemplate(rpcConfigFilename);

    }

    private static void initForMultipleFIle(String configFilenames) {
        Arrays.stream(configFilenames.split(",")).forEach(Core::configureRequestTemplate);
    }

    private static void configureRequestTemplate(String rpcConfigFilename) {
        var rpcJson = JSONUtil.readJSONObject(new File(rpcConfigFilename), StandardCharsets.UTF_8);

        JSONArray tempVar = rpcJson.getJSONArray("variable");
        Core.variables = new HashMap<>();

        tempVar.forEach((varObj) -> {
            var anObj = (JSONObject) varObj;
            Core.variables.put((String) anObj.get("key"), (String) anObj.get("value"));
        });

        JSONArray requestOrFolder = rpcJson.getJSONArray("item");
        recursiveEncapsulateRequest(requestOrFolder);

    }

    private static void recursiveEncapsulateRequest(JSONArray requestOrFolder) {
        if (requestOrFolder.isEmpty()) {
            return;
        }
        requestOrFolder.forEach(obj -> {
            var jsonObj = (JSONObject) obj;
            String name = jsonObj.getStr("name");
            JSONArray item = jsonObj.getJSONArray("item");
            if (item == null || item.isEmpty()) {
                JSONObject request = jsonObj.getJSONObject("request");
                // TODO: 2023/1/30 will handle wht javaassist
                JSONArray response = jsonObj.getJSONArray("response");
                // TODO: 2023/1/30 encapsulate response with name
            } else {
                recursiveEncapsulateRequest(item);
            }
        });
    }

    // TODO: 2023/1/30  will make this call 
    public Object send(String rpcName) {
        Rpc rpc = rpcMap.get(rpcName);
        if (rpc == null) {
            throw new RPCException(rpcName + " not exist !");
        }
        return rpc.send(null);
    }
}
