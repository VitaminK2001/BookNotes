// 连续的字母长度
// 一个字符串只包含大写字母，求在包含同一字母的子串中，长度第k长的子串的那个长度，相同字母只取最长的那个字串
// 输入：
// AAAAHHHBBCDHHHH
// 3
// 输出：
// 2
// 连续出现最多的是A和H，四次
// 第二多的是H，三次，但是H已经存在4个连续的，不考虑
// 下个最长子串是BB，所以最终答案是2

import java.util.*;                

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);  
        String str = input.next();              // 读取字符串输入
        int k = input.nextInt();                // 读取整数k值

        HashSet<Character> charSet = new HashSet<>(); // 创建HashSet，用于存储字符串中不同的字符
        for (char c : str.toCharArray()) {           // 遍历字符串中的每个字符
            charSet.add(c);                          // 将字符添加到HashSet中（自动去重）
        }

        HashMap<Character, Integer> charMap = new HashMap<>(); // 创建HashMap，用于存储每个字符的最长子串长度
        for (char c : charSet) {                               // 遍历HashSet中的每个字符
            // 创建一个正则表达式模式，用于匹配当前字符的连续子串
            Pattern pattern = Pattern.compile(String.valueOf(c) + "+");
            // 使用Matcher在字符串中查找所有匹配的子串
            Matcher matcher = pattern.matcher(str);
            while (matcher.find()) {                         // 当找到匹配的子串时
                int repeatTimes = matcher.group().length();  // 获取匹配子串的长度
                // 更新HashMap中的最长子串长度
                if (charMap.containsKey(c)) { 
                    // 如果当前字符已在Map中，比较并存储最大长度
                    charMap.put(c, Math.max(charMap.get(c), repeatTimes));
                } else {
                    // 如果当前字符不在Map中，直接存储当前长度
                    charMap.put(c, repeatTimes);
                }
            }
        }

        // 将所有字符的最长子串长度存储到ArrayList中
        ArrayList<Integer> values = new ArrayList<>(charMap.values());
        // 对ArrayList中的长度值进行降序排序
        Collections.sort(values, Collections.reverseOrder());
        // 如果k大于ArrayList中的元素数量，返回-1，否则返回第k大的长度值
        int rt = k > values.size() ? -1 : values.get(k - 1);
        System.out.println(rt); // 输出结果
    }
}
