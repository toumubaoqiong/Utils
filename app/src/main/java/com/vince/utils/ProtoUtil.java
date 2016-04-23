package com.vince.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.util.Log;

/**
 * @author Vince
 * @Description: 转换工具类
 */
@SuppressWarnings("unchecked")
public class ProtoUtil {

    public static java.util.Date addYear(java.util.Date date, int year) {
        GregorianCalendar gdate = new GregorianCalendar();
        gdate.setTime(date);
        gdate.add(GregorianCalendar.YEAR, year);
        return gdate.getTime();
    }

    public static java.util.Date addMonth(java.util.Date date, int month) {
        GregorianCalendar gdate = new GregorianCalendar();
        gdate.setTime(date);
        gdate.add(GregorianCalendar.MONTH, month);
        return gdate.getTime();
    }

    public static java.util.Date addDay(java.util.Date date, int day) {
        GregorianCalendar gdate = new GregorianCalendar();
        gdate.setTimeInMillis(date.getTime());
        gdate.add(GregorianCalendar.DAY_OF_MONTH, day);
        return gdate.getTime();
    }

    public static java.util.Date addSecond(java.util.Date date, int second) {
        GregorianCalendar gdate = new GregorianCalendar();
        gdate.setTimeInMillis(date.getTime());
        gdate.add(GregorianCalendar.SECOND, second);
        return gdate.getTime();
    }

    public static java.util.Date addMinute(java.util.Date date, int minute) {
        GregorianCalendar gdate = new GregorianCalendar();
        gdate.setTimeInMillis(date.getTime());
        gdate.add(GregorianCalendar.MINUTE, minute);
        return gdate.getTime();
    }

    /**
     * @param @param  date
     * @param @param  patter
     * @param @return
     * @return String
     * @Title: formatDate
     * @Description: 获取指定格式日期字符串
     */
    public static String formatDate(Date date, String patter) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(patter);
            return sdf.format(date);
        } catch (RuntimeException e) {
            return "";
        }
    }

    /**
     * @param @param  date
     * @param @return
     * @return String
     * @Title: formatDate
     * @Description: 按默认格式格式化时间
     */
    public static String formatDate(Date date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(date);
        } catch (RuntimeException e) {
            return "";
        }
    }

    /**
     * 按照指定的格式来解析字符串成为时间
     *
     * @param date
     * @param patter
     * @return
     */
    public static Date parseDate(String strDate, String patter) {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat(patter);
        try {
            date = sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return date;
    }

    /**
     * @param @param  strDate
     * @param @return
     * @return Date
     * @Title: parseDate
     * @Description: 按默认格式解析时间
     */
    public static Date parseDate(String strDate) {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return date;
    }

    /**
     * 获得现在的时间(格式:yyyy-MM-dd)
     *
     * @return
     */
    public static Date getNow() {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(dateFormat.format(now));
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date getTimeDate() {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        try {
            return dateFormat.parse(dateFormat.format(now));
        } catch (ParseException e) {
            return null;
        }
    }

    public static int weeksBetween(Date date1, Date date2) {
        int days = daysBetween(date1, date2);
        return (days % 7) == 0 ? (days / 7) : (days / 7) + 1;
    }

    /**
     * 计算两个时间相差的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int daysBetween(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return 0;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * @param @param  time1
     * @param @param  time2
     * @param @return
     * @return int
     * @Title: getTimestamp
     * @Description: 获取1-2的时间差，单位为ms
     */
    public static long getTimestamp(Date time1, Date time2) {
        if (time1 == null || time2 == null) {
            return 0;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(time1);
        long m1 = cal.getTimeInMillis();
        cal.setTime(time2);
        long m2 = cal.getTimeInMillis();
        long between_sec = (m2 - m1);
        return between_sec;
    }

    /**
     * @param @param  offset 星期偏移量，0为本周，-1为上周，1为下周，如此类推
     * @param @return date[0]：开始时间，格式2012-03-04
     *                00:00:00；date[1]：结束时间，格式2012-03-10 23:59:59；异常为null
     * @return Date[]
     * @Title: getWeekTimesBE
     * @Description: 获取一周的起止时间
     */
    public static Date[] getWeekTimesBE(int offset) {
        try {
            Date[] dates = new Date[2];
            SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat f2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // 得到当前日期
            Calendar cal = Calendar.getInstance();

            // 得到本周第一天日期
            int day_of_week = cal.get(Calendar.DAY_OF_WEEK);
            cal.add(Calendar.DATE, -day_of_week + 1 + (7 * offset));
            Date begin = cal.getTime();
            String weekFirstStr = f1.format(begin);
            begin = f2.parse(weekFirstStr + " 00:00:00");

            // 得到本周最后一天
            cal.add(Calendar.DATE, 6);
            Date end = cal.getTime();
            String weekLastStr = f1.format(end);
            end = f2.parse(weekLastStr + " 23:59:59");
            dates[0] = begin;
            dates[1] = end;
            return dates;
        } catch (Exception e) {
            return null;
        }
    }

    public static Date[] getDayBE(Date date) {
        Date[] dates = new Date[2];
        String strDate = formatDate(date, "yyyy-MM-dd");// 今天
        Date start = parseDate(strDate + " 00:00:00",
                "yyyy-MM-dd HH:mm:ss");
        Date end = parseDate(strDate + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
        dates[0] = start;
        dates[1] = end;
        return dates;
    }

    /**
     * 判断当前日期是星期几<br>
     * <br>
     *
     * @param pTime 修要判断的时间<br>
     * @return dayForWeek 判断结果<br>
     * @Exception 发生异常<br>
     */
    public static int dayForWeek(String pTime) {
        int dayForWeek = 0;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            c.setTime(format.parse(pTime));

            if (c.get(Calendar.DAY_OF_WEEK) == 1) {
                dayForWeek = 7;
            } else {
                dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
            }
        } catch (Exception e) {
            Log.d("dayForWeek", e.getMessage());
        }
        return dayForWeek;
    }

    /**
     * @param @param  offset 月份偏移量，0为本月，-1为上月，1为下月，如此类推
     * @param @return date[0]：开始时间，格式2012-03-01
     *                00:00:00；date[1]：结束时间，格式2012-03-31 23:59:59；异常为null
     * @return Date[]
     * @Title: getMonthTimesBE
     * @Description: 获取一月的起止时间
     */
    public static Date[] getMonthTimesBE(int offset) {
        try {
            Date[] dates = new Date[2];
            // 得到当前日期
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, offset);

            int MaxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            // 按你的要求设置时间
            cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), MaxDay,
                    23, 59, 59);
            Date end = cal.getTime();
            cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1, 00, 00,
                    00);
            Date begin = cal.getTime();
            dates[0] = begin;
            dates[1] = end;
            return dates;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param @param  date
     * @param @return
     * @return Date[]
     * @Title: getDayTimesBE
     * @Description: 获取一天的开始结束时间
     */
    public static Date[] getDayTimesBE(Date date) {
        Date[] dates = new Date[2];
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        // 按你的要求设置时间
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        Date end = cal.getTime();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH), 00, 00, 00);
        Date begin = cal.getTime();
        dates[0] = begin;
        dates[1] = end;
        return dates;
    }

    /**
     * @param @param  list
     * @param @return
     * @return boolean
     * @Title: isNotEmpty
     * @Description: 判断列表是否为空
     */
    public static boolean isNotEmpty(List list) {
        boolean returnBoolean = false;
        if (list != null && list.size() > 0) {
            returnBoolean = true;
        }

        return returnBoolean;
    }

    /**
     * @param @param  ObjectArray
     * @param @return
     * @return boolean
     * @Title: isNotEmpty
     * @Description: 判断数组是否为空
     */
    public static boolean isNotEmpty(Object[] ObjectArray) {
        boolean returnBoolean = false;
        if (ObjectArray != null && ObjectArray.length > 0) {
            returnBoolean = true;
        }

        return returnBoolean;
    }

    /**
     * @param @param  strings
     * @param @return
     * @return boolean
     * @Title: isNotEmpty
     * @Description: 判断字符串是否为空
     */
    public static boolean isNotEmpty(String... strings) {
        boolean returnBoolean = true;

        if (strings != null && strings.length > 0) {
            for (String string : strings) {
                if (string == null || "".equals(string)) {
                    returnBoolean = false;
                    break;
                }
            }
        } else {
            returnBoolean = false;
        }

        return returnBoolean;
    }

    /*
     * @purpose:calculate the page count using the count and pagesize @params:
     * int ,int @return: int
     */
    public static int count2PageCount(int count, int pageSize) {
        int pageCount = count / pageSize;
        if (count % pageSize != 0) {
            pageCount++;
        }

        return pageCount;
    }

    /*
     * @purpose:calculate the page count using the count and pagesize @params:
     * int ,int @return: int
     */
    public static int count2PageCount(long countLong, int pageSize) {
        int count = Long.valueOf(countLong).intValue();
        int pageCount = count / pageSize;
        if (count % pageSize != 0) {
            pageCount++;
        }

        return pageCount;
    }

    /*
     * @purpose:put a List to String use the specified pattern ";" @params: List
     *
     * @return: String
     */
    public static String listToString(List list) {
        StringBuffer stringBuffer = new StringBuffer();

        if (isNotEmpty(list)) {
            for (Object object : list) {
                if (object != null) {
                    stringBuffer.append(object.toString());
                    stringBuffer.append(";");
                }
            }
        }

        if (stringBuffer.length() == 0) {
            return null;
        } else {
            return stringBuffer.toString();
        }
    }

    /*
     * @purpose:put a List to String use the specified pattern @params: List
     *
     * @return: String
     */
    public static String listToString(List list, String pattern) {
        StringBuffer stringBuffer = new StringBuffer();

        if (isNotEmpty(list)) {
            for (Object object : list) {
                if (object != null) {
                    stringBuffer.append(object.toString());
                    stringBuffer.append(pattern);
                }
            }
        }

        if (stringBuffer.length() == 0) {
            return null;
        } else {
            return stringBuffer.toString();
        }
    }

    /*
     * @purpose:put a array to a String using the specified pattern ";" @params:
     * Object[] @return: String
     */
    public static String array2String(Object[] objects) {
        String string = null;

        StringBuffer stringBuffer = new StringBuffer();
        if (isNotEmpty(objects)) {
            for (Object object : objects) {
                if (object != null) {
                    stringBuffer.append(object.toString());
                    stringBuffer.append(";");
                }
            }
        }

        if (stringBuffer.length() > 0) {
            string = stringBuffer.toString();
        }

        return string;
    }

    /*
     * @purpose:put a Object Array to List @params: Object[] @return: List
     */
    public static List arrayToList(Object[] objects) {
        List list = null;

        if (isNotEmpty(objects)) {
            list = new ArrayList();
            for (Object object : objects) {
                if (object != null) {
                    list.add(object);
                }
            }
        }

        return list;
    }

    /*
     * @purpose:put a String array to List<Integer> @params: String[] @return:
     * List<Integer>
     */
    public static List<Integer> stringArrayToListInteger(String[] strings) {
        List<Integer> list = null;

        try {
            if (isNotEmpty(strings)) {
                list = new ArrayList<Integer>();
                for (String string : strings) {
                    if (string != null) {
                        list.add(Integer.parseInt(string));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            list = null;
        }

        return list;
    }

    /*
     * @purpose:put a String to List<String> based on the specified pattern ";"
     *
     * @params: String @return: List<String>
     */
    public static List<String> stringToList(String string) {
        List<String> list = null;

        if (isNotEmpty(string)) {
            String[] stringArray = string.split(";");
            list = arrayToList(stringArray);
        }

        return list;
    }

    // 解析输入流成byte数组
    public static byte[] recvMsg(InputStream inputstream, int length)
            throws Exception {
        try {
            byte content[] = new byte[length];
            int readCount = 0; // 已经成功读取的字节的个数
            while (readCount < length) {
                int size = (length - readCount) > 1024 ? 1024
                        : (length - readCount);
                readCount += inputstream.read(content, readCount, size);
            }
            return content;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @return boolean
     * @Title: isNumeric
     * @Description: 判断字符串是否为数字
     * @Param @param str
     * @Param @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    /**
     * 密码验证（包含数字和字母）
     *
     * @param pwd
     * @param minLen :最新长度
     * @param maxLen :最大长度
     * @return
     * @create_date 2014-8-29 上午9:16:02
     */
    public static boolean isOK2Pwd(String pwd, int minLen, int maxLen) {
        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{" + minLen + ","
                + maxLen + "}$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(pwd).matches();
    }

    /**
     * 匹配纯字符
     *
     * @param pwd
     * @param minLen
     * @param maxLen
     * @return
     * @author zhr
     * @create_date 2014-9-26 下午5:37:57
     */
    public static boolean isOK2Pwd2(String pwd, int minLen, int maxLen) {
        String regex = "^[A-Za-z]{" + minLen + "," + maxLen + "}$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(pwd).matches();
    }

    /**
     * 匹配纯数字
     *
     * @param pwd
     * @param minLen
     * @param maxLen
     * @return
     * @author zhr
     * @create_date 2014-9-26 下午5:36:59
     */
    public static boolean isOK2Pwd3(String pwd, int minLen, int maxLen) {
        String regex = "^[0-9]{" + minLen + "," + maxLen + "}$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(pwd).matches();
    }

    /**
     * 判断手机号是否合法
     *
     * @param phone
     * @return
     */
    public static boolean isPhone(String phone) {
        if (null == phone || "".equals(phone)) {
            return false;
        }
        String regExp = "^1[3,5,8]{1}[0-9]{1}[0-9]{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(phone);
        return m.find();
    }

    /**
     * 判断邮箱号是否合法
     *
     * @param phone
     * @return
     */
    public static boolean isEmail(String email) {
        if (null == email || "".equals(email)) {
            return false;
        }
        String regExp = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(email);
        return m.find();
    }

    /**
     * @param @param  list 要转换的对象
     * @param @param  split 分隔符
     * @param @return
     * @return String
     * @Title: parseListToString
     * @Description: 列表转换为字符串
     */
    public static String parseListToString(List list, String split) {
        if (list != null && list.size() > 0) {
            String str = "";
            int len = list.size();
            for (int i = 0; i < len; i++) {
                if (i != (len - 1)) {
                    str += (list.get(i) + split);
                } else {
                    str += list.get(i);
                }
            }
            return str;
        }
        return null;
    }

    /**
     * @param @param  arr
     * @param @param  split
     * @param @return
     * @return String
     * @Title: pasreArrayToString
     * @Description: 数组转字符串
     */
    public static String pasreArrayToString(Object[] arr, String split) {
        if (arr != null && arr.length > 0) {
            String str = "";
            for (int i = 0; i < arr.length; i++) {
                if (i != (arr.length - 1)) {
                    str += (arr[i] + split);
                } else {
                    str += arr[i];
                }
            }
            return str;
        }
        return null;
    }

    /**
     * @param @return
     * @return String
     * @Title: getTimeFileName
     * @Description: 获取默认的以是时间命名的文件名
     */
    public static String getTimeFileName(String suffix) {
        return formatDate(new Date(), "yyyyMMddHHmmssSSS") + suffix;
    }

    /**
     * @param @param  birthday
     * @param @return
     * @return String
     * @Title: getAgeByBirthday
     * @Description: 计算年龄
     */
    public static String getAgeByBirthday(Date birthday) {
        int days = daysBetween(birthday, new Date());
        int year = days / 365;
        int month = (days % 365) / 30;
        String age = "";
        if (year > 0) {
            age = year + "岁";
            if (month > 0) {
                age = age + month + "个月";
            }
        } else if (month > 0) {
            age = month + "个月";
        }
        return age;
    }

    public static byte[] getImageToBytes(String imgPath) {
        byte[] bytes = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            // 创建URL
            URL url = new URL(imgPath);
            // 得到连接
            HttpURLConnection urlConn = (HttpURLConnection) url
                    .openConnection();
            // 得到连接地址的输入流
            InputStream in = urlConn.getInputStream();

            int size;
            // 缓冲值
            bytes = new byte[1024];
            if (in != null) {
                // 循环读输入流至read返回-1为止，并写到缓存中
                while ((size = in.read(bytes)) != -1) {
                    out.write(bytes, 0, size);
                }
            }
            out.close();// 关闭输出流
            in.close();// 关闭输入流
            urlConn.disconnect();// 断开连接

        } catch (Exception e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }

    /**
     * @param @param  date
     * @param @return
     * @return String
     * @Title: getDateTreeDir
     * @Description: 组装根据年/月/日格式的文件目录 如：2012/04/20/
     */
    public static String getDateTreeDir(Date date) {
        return formatDate(date, "yyyy") + "/"
                + formatDate(date, "MM") + "/"
                + formatDate(date, "dd") + "/";
    }

    public static byte[] getFile2Bytes(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    public static Integer string2Integer(String s) {
        Integer rs = null;
        try {
            if (s != null && Pattern.compile("[0-9]*").matcher(s).matches()) {
                rs = Integer.parseInt(s);
            }
        } catch (Exception e) {
            return null;
        }
        return rs;
    }

    /**
     * 去掉开头和结尾的空格，合并中间的空格
     */
    public static String mergeSpace(String str) {
        String result = "";
        for (int i = 0; i < str.length(); i++) {
            if (str.substring(i, i + 1).equals(" ") == true) {
                if (result.equals("") == true) {
                } else {
                    if (result.equals("") != true
                            && result.substring(result.length() - 1)
                            .equals(" ") == true) {
                        continue;
                    } else {
                        result = result + str.substring(i, i + 1).toString();
                    }
                }
            } else {
                result = result + str.substring(i, i + 1).toString();
            }
            if (i == str.length() - 1) {
                if (result.substring(result.length() - 1).equals(" ") == true) {
                    result = result.substring(0, result.length() - 1)
                            .toString();
                }
            }
        }
        return result;
    }

    /**
     * 生成随机的字符串
     *
     * @param length
     * @return
     * @create_date 2014-10-13 下午6:41:17
     */
    public static String getCharacterAndNumber(int length) {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) // 字符串
            {
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; // 取得大写字母还是小写字母
                val += (char) (choice + random.nextInt(26));
            } else if ("num".equalsIgnoreCase(charOrNum)) // 数字
            {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

    /**
     * 生成带时间因子的唯一字符串
     *
     * @param childrandom
     * @return
     * @create_date 2014-10-14 下午6:02:20
     */
    public static String getUuid(String childrandom) {
        String child = getCharacterAndNumber(6);
        String time = new Date().getTime() + "";
        return time.substring(0, 8) + child
                + time.substring(9, time.length() - 1);

    }

    public static Map<String, String> getUrlInfo(String url) {
        if (url == null)
            return null;
        Map<String, String> map = new HashMap<String, String>();
        try {
            URL hp = new URL(url);
            map.put("protocol", hp.getProtocol()); // 协议
            map.put("port", hp.getPort() + ""); // 端口
            map.put("host", hp.getHost()); // 主机
            map.put("filename", hp.getFile()); // url对应的文件名
            map.put("ext", hp.toExternalForm());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 获取百分数
     *
     * @param d
     * @param point ：小数点后的位数
     * @return
     * @create_date 2014-11-13 下午3:16:20
     */
    public static String getPercent(double d, int point) {
        NumberFormat nt = NumberFormat.getPercentInstance();
        nt.setMinimumFractionDigits(point);// 设置百分数精确度2即保留两位小数
        return nt.format(d);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
     *
     * @param v1    除数
     * @param v2    被除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        double rs = b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP)
                .doubleValue();
        BigDecimal bg = new BigDecimal(rs);
        return bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 将长整型数字转换为日期格式的字符串
     *
     * @param time
     * @param format
     * @return
     */
    public static String getTimeString(long timelnMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timelnMillis);
        Date date = calendar.getTime();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String newTypeDate = f.format(date);
        return newTypeDate;
    }

    /**
     * 格式化时间
     *
     * @param time
     * @return
     */
    public static String formatTime(long time) {
        DateFormat formatter = new SimpleDateFormat("mm:ss");
        return formatter.format(new Date(time));
    }

    /**
     * 转换为M或者k
     *
     * @return
     */
    public static String convertKorM(long size) {
        if (size / 1024 / 1024 == 0) {
            return Math.round(size / 1024.0 * 100) / 100.0 + "KB";//返回k
        } else {
            return Math.round(size / 1024.0 / 1024 * 100) / 100.0 + "MB";//返回M
        }
    }
}

