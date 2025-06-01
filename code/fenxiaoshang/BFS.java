import java.util.*;

public class BFS {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine().trim());

        HashMap<Integer, Long> income = new HashMap<>();
        HashSet<Integer> ids = new HashSet<>();
        HashMap<Integer, Integer> childToParent = new HashMap<>();
        HashMap<Integer, List<Integer>> parentToChildren = new HashMap<>();
        HashMap<Integer, Integer> inDegree = new HashMap<>(); // 记录每个节点未处理的子节点数

        for (int i =  0; i < n; i++) {
            String[] parts = sc.nextLine().split(" ");
            int childId = Integer.parseInt(parts[0]);
            int parentId = Integer.parseInt(parts[1]);
            long childIncome = Long.parseLong(parts[2]);

            income.put(childId, childIncome);
            ids.add(childId);
            ids.add(parentId);
            childToParent.put(childId, parentId);
            
            parentToChildren.putIfAbsent(parentId, new ArrayList<>());
            parentToChildren.get(parentId).add(childId);
            
            // 初始化inDegree
            inDegree.put(parentId, inDegree.getOrDefault(parentId, 0) + 1);
            inDegree.putIfAbsent(childId, 0);
        }

        // 找出顶级分销商
        int boss = -1;
        for (int id : ids) {
            if (!childToParent.containsKey(id)) {
                boss = id;
                income.putIfAbsent(boss, 0L);
                break;
            }
        }

        // BFS处理
        Queue<Integer> queue = new LinkedList<>();
        // 找出所有叶子节点（inDegree为0的节点）
        for (int id : ids) {
            if (inDegree.getOrDefault(id, 0) == 0) {
                queue.offer(id);
            }
        }

        while (!queue.isEmpty()) {
            int current = queue.poll();
            if (!childToParent.containsKey(current)) {
                continue; // 已经到达boss节点
            }
            
            int parent = childToParent.get(current);
            // 计算当前节点对父节点的贡献
            long contribution = income.get(current) / 100 * 15;
            income.put(parent, income.getOrDefault(parent, 0L) + contribution);
            
            // 减少父节点的inDegree
            int newDegree = inDegree.get(parent) - 1;
            inDegree.put(parent, newDegree);
            
            // 如果父节点已经没有未处理的子节点了，加入队列
            if (newDegree == 0) {
                queue.offer(parent);
            }
        }

        System.out.println(boss + " " + income.get(boss));
    }
}