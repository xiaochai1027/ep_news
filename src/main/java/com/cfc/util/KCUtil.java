package com.cfc.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cfc.util.exception.Errors;
import com.cfc.util.exception.KickOffException;
import com.cfc.util.exception.SysException;
import com.cfc.util.model.PageModel;


import javax.servlet.http.HttpServletRequest;
import java.util.*;


public class KCUtil {


    public static String ListMapToString(List col, String key, String sep) {
        StringBuilder sb = new StringBuilder();

        col.forEach(x -> {
            sb.append(((Map) x).get(key)).append(sep);
        });

        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }

    public static String ListMapToDString(List col, String key, String sep) {

        Set keyValuesSet = new HashSet();

        col.forEach(x -> {
            keyValuesSet.add(((Map) x).get(key));

        });

        StringBuilder sb = new StringBuilder();

        keyValuesSet.forEach(vaule -> {
            sb.append(vaule).append(sep);
        });

        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }

    public static String ListToString(Collection col, String sep) {
        StringBuilder sb = new StringBuilder();

        col.forEach(x -> {
            sb.append(x).append(sep);
        });

        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }

    public static <T> List<T> getPageResults(Integer start, Integer count, List<T> results) {

        int from = start;
        int to = start + count;

        if (from >= results.size()) {
            return new ArrayList<>();
        }

        if (to > results.size()) {
            to = results.size();
        }
        return results.subList(from, to);
    }

    public static <T> List<T> getPageResults(PageModel page, List<T> results) {
        return getPageResults(page.getStart(), page.getCount(), results);
    }


    public static void dealErrorCode(JSONObject jsonResult) {

        Integer errorCode = jsonResult.getInteger("errorCode");
        if (errorCode != null) {
            if (errorCode == Errors.ERROR_TOKEN_KICK_OFF) {
                KickOffException kickOffException = new KickOffException(errorCode);
                kickOffException.setExData(jsonResult.getJSONObject("errorInfo"));
                throw kickOffException;
            }
            throw new SysException(errorCode, jsonResult.getString("errorMsg"));
        }
    }

    public static Pair<JSONArray, Integer> resultPageList(JSONObject ret) {
        dealErrorCode(ret);
        if (ret.containsKey("results")) {
            JSONArray list;
            if (ret.get("results") == null) {
                list = new JSONArray();
            } else {
                list = ret.getJSONArray("results");
            }
            Integer totalCount = ret.getJSONObject("page").getInteger("totalCount");
            Pair<JSONArray, Integer> retPair = new Pair<>(list, totalCount);
            return retPair;
        } else {
            throw new SysException(ret);
        }
    }

    public static JSONArray resultList(JSONObject ret) {
        dealErrorCode(ret);
        if (ret.containsKey("results")) {
            JSONArray list;
            if (ret.get("results") == null) {
                list = new JSONArray();
            } else {
                list = ret.getJSONArray("results");
            }
            return list;
        } else {
            throw new SysException(ret);
        }
    }

    public static Boolean resultBoolean(JSONObject ret) {
        dealErrorCode(ret);
        if (ret.containsKey("results")) {
            Boolean b;
            if (ret.get("results") == null) {
                b = false;
            } else {
                b = ret.getBoolean("results");
            }
            return b;
        } else {
            throw new SysException(ret);
        }
    }

    public static JSONObject resultObject(JSONObject ret) {
        dealErrorCode(ret);
        JSONObject object;
        if (ret.get("results") == null) {
            object = new JSONObject();
        } else {
            object = ret.getJSONObject("results");
        }
        if (ret.containsKey("results")) {
            return object;
        } else {
            throw new SysException(ret);
        }
    }

    public static Integer resultInteger(JSONObject ret) {
        dealErrorCode(ret);
        Integer object;
        if (ret.get("results") == null) {
            object = 0;
        } else {
            object = ret.getInteger("results");
        }
        if (ret.containsKey("results")) {
            return object;
        } else {
            throw new SysException(ret);
        }
    }


    public static String resultString(JSONObject ret) {
        dealErrorCode(ret);
        if (ret.containsKey("results")) {
            return ret.getString("results");
        } else {
            throw new SysException(ret);
        }
    }


    public static Map<String, Object> getParameterMap(HttpServletRequest req) {
        Map<String, Object> map = new HashMap<>();
        req.getParameterMap().keySet().forEach(key -> {
            map.put(key, req.getParameter(key));
        });
        return map;
    }


    /**
     * 排序列表
     *
     * @param list    列表
     * @param columns first：字段名 second：true升序 false降序
     */
    @SuppressWarnings("unchecked")
    @SafeVarargs
    public static void sortMultiColumns(List<Map<String, Object>> list, Pair<String, Boolean>... columns) {
        for (int i = 0; i < columns.length; i++) {
            final int currIndex = i;
            list.sort((x, y) -> {
                //比较之前的列是否都相等
                boolean equals = true;
                for (int j = 0; j < currIndex; j++) {
                    Object xPreColumnValue = x.get(columns[j].first);
                    Object yPreColumnValue = y.get(columns[j].first);
                    if (xPreColumnValue == null || yPreColumnValue == null) {
                        equals = false;
                        break;
                    }
                    if (!xPreColumnValue.equals(yPreColumnValue)) {
                        equals = false;
                        break;
                    }
                }
                int ret = 0;
                if (equals) {//如果之前的列都相等，则比较当前列
                    boolean isASC = columns[currIndex].second;
                    Comparable xColumnValue = (Comparable) x.get(columns[currIndex].first);
                    Comparable yColumnValue = (Comparable) y.get(columns[currIndex].first);

                    if (yColumnValue == null && xColumnValue == null) {
                        return 0;
                    }
                    if (xColumnValue == null) {
                        return -1;
                    }
                    if (yColumnValue == null) {
                        return 1;
                    }
                    if (isASC) {
                        ret = xColumnValue.compareTo(yColumnValue);
                    } else {
                        ret = yColumnValue.compareTo(xColumnValue);
                    }
                }
                return ret;
            });
        }
    }
}
