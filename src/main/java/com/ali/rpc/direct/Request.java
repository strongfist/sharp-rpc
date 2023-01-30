package com.ali.rpc.direct;

import java.util.List;
import java.util.Map;

public class Request<I, O> {
    String method;
    String[] headers;

    /* "url": {
							"raw": "{{simple-api}}/books/:bookId",
							"host": [
								"{{simple-api}}"
							],
							"path": [
								"books",
								":bookId"
							]
    */
    //    {{simple-api}}/books?type=fiction&limit=20
    String url;
    String[] path;
    //    "query": [
//    {
//        "key": "type",
//            "value": "fiction"
//    },
//    {
//        "key": "limit",
//            "value": "20"
//    }
//							]
    List<Map<String, String>> query;

    // TODO: 2023/1/30  path have some connection to variable 
    /* "path": [
								"orders",
								":orderid"
							],
							"variable": [
								{
									"key": "orderid",
									"value": "{{orderId}}"
								}
							] */
    List<Map<String, String>> variable;
    /* "auth": {
                                "type": "bearer",
                                "bearer": [
                                    {
                                        "key": "token",
                                        "value": "{{acces-token}}",
                                        "type": "string"
                                    }
                                ]
                            }
    */
    Map<String, Object> auth;
    // TODO: 2023/1/30  and file form data 
    /* "body": {
							"mode": "formdata",
							"formdata": [
								{
									"key": "form-data",
									"type": "file",
									"src": "/Users/almasrixat/Desktop/almas.5544.png"
								}
							] */
    /* "body": {
							"mode": "raw",
							"raw": "{\n    \"bookId\": 1,\n    \"customerName\": \"{{$randomFullName}}\"\n}",
							"options": {
								"raw": {
									"language": "json"
								}
							}
						} */
    Map<String, Object> body;

    O response;


}
