package com.taf.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by AD97137 on 3/28/2016.
 */
public class JsonUtils {

    /**
     * Reads in several Cucumber JSON result files (each of which contains an array), merges their contents into a
     * single array, and outputs the merged JSON to a file.
     * @param mergedJsonFile
     * @param jsonFilesToMerge
     * @throws IOException
     */
    public static void mergeCucumberReports(File mergedJsonFile, List<File> jsonFilesToMerge) throws IOException, MergeException {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode jsonMergedArray = JsonNodeFactory.instance.arrayNode();

        Set<String> setIDs = new HashSet<>();
        Set<String> setDuplicateIDs = new HashSet<>();
        if (jsonFilesToMerge.size() > 0) {
            for (File jsonFile : jsonFilesToMerge) {
                JsonNode jsonNode = mapper.readTree(jsonFile);
                if (jsonNode.isArray()) {
                    ArrayNode jsonArray = (ArrayNode) jsonNode;
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JsonNode jsonTestResult = jsonArray.get(i);

                        // Check for duplicate keys
                        String id = jsonTestResult.get("id").asText();
                        if (!setIDs.add(id)) {
                            setDuplicateIDs.add(id);
                        }

                        jsonMergedArray.add(jsonTestResult);
                    }
                } else {
                    throw new IOException("JSON file contains a single element, but expected an array: " + jsonFile.getAbsolutePath());
                }
            }

//            if (setDuplicateIDs.size() > 0) {
//                throw new MergeException("Duplicate keys: " + setDuplicateIDs.toString());
//            }
        }

        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(mergedJsonFile, jsonMergedArray);
    }

    public static class MergeException extends Exception {
        public MergeException(String msg) {
            super(msg);
        }
    }
}
