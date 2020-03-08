package za.co.bvr.forth.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.logging.Logger;
import lombok.extern.java.Log;
import za.co.bvr.forth.dtos.MapDto;
import za.co.bvr.forth.dtos.StringListDto;

/**
 *
 * @author nicm
 */

@Log
public class JsonUtilities {
    private static final Logger log = Logger.getLogger("JsonUtilities");
    
    public static String toJsonString(StringListDto stringList){
        Gson gson = new Gson();
        String json = gson.toJson(stringList);
        return json;
    }
    
    
    public static String toJsonString(MapDto map){
        Gson gson = new Gson();
        String json = gson.toJson(map);
        return json;
    }
    
    public static String toJsonPrettyPrintString(StringListDto stringList){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(stringList);
        return json;
    }
    
    
    public static String toJsonPrettyPrintString(MapDto map){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(map);
        return json;
    }
    
    public static StringListDto jsonStringToStringListDto(String json){
        Gson gson = new Gson();
        StringListDto stringList=gson.fromJson(json, StringListDto.class);
        return stringList;
    }
    
    
    
    public static MapDto jsonStringToStringMapDto(String json){
        Gson gson = new Gson();
        MapDto stringList=gson.fromJson(json, MapDto.class);
        return stringList;
    }
    
    public static void writeJsonStringToFile(StringListDto stringList,String fileSpec) throws IOException{
        Gson gson = new Gson();
        FileWriter writer =new FileWriter(fileSpec);
        gson.toJson(stringList, writer);
    }
    
    public static void writeJsonStringToFile(MapDto map,String fileSpec) throws IOException{
        Gson gson = new Gson();
        FileWriter writer =new FileWriter(fileSpec);
        gson.toJson(map, writer);
    }
    
    public static void writeJsonPrettyPrintStringToFile(StringListDto stringList,String fileSpec) throws IOException{
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileWriter writer =new FileWriter(fileSpec);
        gson.toJson(stringList, writer);
    }
    
    public static void writeJsonPrettyPrintStringToFile(MapDto map,String fileSpec) throws IOException{
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileWriter writer =new FileWriter(fileSpec);
        gson.toJson(map, writer);
    }
    
    public static StringListDto readStringListFromJsonFile(String fileSpec) throws IOException{
        Gson gson = new Gson();
        Reader reader = new FileReader(fileSpec);
        StringListDto stringList=gson.fromJson(reader, StringListDto.class);
        return stringList;
    }
    
    public static MapDto readMapFromJsonFile(String fileSpec) throws IOException{
        Gson gson = new Gson();        
        Reader reader = new FileReader(fileSpec);
        MapDto map=gson.fromJson(reader, MapDto.class);
        return map;
    }
    
}
