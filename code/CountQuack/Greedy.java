// 大雁南飞，给出游客在地面上听到的叫声的字符串，问叫声最少由几只大雁发出

// 1. 大雁发出的完整叫声 为 quack 因为有多只大雁 同一时间嘎嘎作响，字符串中可能混合多个 'quack'
// 2. 如果 字符串中'quack'这5个字母没有按顺序或者不完整则不予计数
// 3. 如果 字符串不是由 'quack' 字符组合而成 或者没有找到一致大雁 返回-1

// 输入：一个字符串，包含大雁quack的叫声。1 <= 字符串长度 <= 1000，字符串中的字符只有’q’, ‘u’, ‘a’, ‘c’, ‘k’。
// 输出：大雁的数量

import java.util.*;

public class Greedy {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String quack = "quack";
        String input = sc.nextLine();
        int strLen = input.length();
        int[] states = new int[quack.length()];
        int max_ = 0;
        ArrayList<Integer> dp = new ArrayList();

        for(int i = 0; i < strLen; ++i) {
            int index = quack.indexOf(input.charAt(i));
            if(index == -1) {
                System.out.println(-1);
                System.exit(0);
            }

            if(index == 0) {
                states[index] += 1;
            }else {
                if(states[index-1] > 0) {
                    states[index-1]--;
                    states[index]++;
                }
                if(index == quack.length()-1) {
                    if(states[states.length-1] != 0) {
                        int[] temp = Arrays.copyOf(states, states.length);
                        temp[states.length-1] = 0;
                        max_ = Math.max(max_, Arrays.stream(temp).sum());
                        for(int j = i; j < strLen; ++j) {
                            index = quack.indexOf(input.charAt(j));
                            if(index > 0 && temp[index-1] > 0) {
                                temp[index-1]--;
                                temp[index]++;
                            }
                            if(temp[states.length-1] == max_) {
                                break;
                            }
                        }
                        dp.add(temp[states.length-1] + 1);
                        states[states.length-1] -= 1;
                    }
                }
            }
        }
        System.out.println(dp.isEmpty() ? -1 : (int) Collections.max(dp));
    }
}