package _02分类算法._08暴力递归_动态规划;

import org.junit.Test;

/*
 * 题目:给定一个整型数组arr，代表数值不同的纸牌排成一条线。玩家A和玩家B依次拿走每张纸牌，
 * 规定玩家A先拿，玩家B后拿，但是每个玩家每次只能拿走最左或最右的纸牌，玩家A和玩家B都绝顶聪明。
 * 请返回最后获胜者的分数。
 【举例】
	例如:sarr=[1,2,100,4]。
 开始时玩家A只能拿走1或4。如果玩家A拿走1，则排列变为[2,100,4]，接下来玩家B可以拿走2或4，然后继续轮到玩家A。
 如果开始时玩家A拿走4，则排列变为[1,2,100]，接下来玩家B可以拿走1或100，然后继续轮到玩家A。
 玩家A作为绝顶聪明的人不会先拿4，因为拿4之后，玩家B将拿走100。
 所以玩家A会先拿1，让排列变为[2,100,4]，接下来玩家B不管怎么选，100都会被玩家A拿走。玩家A会获胜，分数为101。所以返回101。
	例如:arr=[1,100,2]。
 开始时玩家A不管拿1还是2，玩家B作为绝顶聪明的人，都会把100拿走。玩家B会获胜，分数为100。所以返回100

 思路:暴力递归法:
 1.定义递归函数frist(i,j),表示如果arr[i..j]这个排列上的纸牌被绝顶聪明的人先拿，最终获得的分数，
 2.定义递归函数second(i,j),表示如果arr[i..j]这个排列上的纸牌被聪明的人后拿，最终能获得的分数
 3.先来分析frist(i,j),具体过程如下：
 	1 如果 i==j，那么arr[i..j] 上只剩下一张牌，就会被先拿纸牌的人拿走，arr[i]
 	2 如果 i!=j。当前拿纸牌的人有两种选择，要么拿走arr[i],要么拿走arr[j],
 如果拿走arr[i],那么排列将剩下arr[i+1..j],对于当前玩家来说，面对arr[i+1..j]排列的纸牌，它成了后拿的人，所以后续它能获得分数为second(i+1,j)
 作为绝顶聪明的人，必然会在两种决策中选择最优的，所以返回 max(arr[i]+second(i+1,j),arr[j]+second(i,j-1))
 4.然后分析second(i,j),具体过程如下：
	 1.如果 i==j，那么arr[i..j] 上只剩下一张牌，作为在这个范围后拿纸牌的人，必然什么也拿不到，返回0
	 2.如果i!=j 根据函数second的定义，玩家的对手会先拿到牌。对手要么拿走arr[i],要么拿走arr[j],
 如果对手拿走arr[i],那么排列将剩下arr[i+1..j],然后轮到玩家先拿，
 如果对手拿走arr[j],那么排列将剩下arr[i..j-1],然后轮到玩家先拿。
 对手也是绝顶聪明的人，所以必然会把最差的情况留给玩家，所以返回min(frist(i+1,j),frist(i,j-1))
 */
public class _03纸牌博弈问题_暴力 {

	@Test
	public void main() {
		int[] arr = { 1, 2, 100, 4 };
		int print1 = getWinPoint_baoli(arr);
		System.out.println(print1);
	}

	// 暴力递归法法
	private int getWinPoint_baoli(int[] arr) {
		// basecase
		if (arr == null || arr.length == 0) {
			return 0;
		}
		// 选择先拿或后拿人中比较大的那个返回(参数是arr两端元素的下标)
		return Math.max(frist(arr, 0, arr.length - 1),
				second(arr, 0, arr.length - 1));
	}

	//定义先拿人的函数(返回它的分数)
	private int frist(int[] arr, int i, int j) {
		if (i == j) {
			return arr[i]; // 如果只剩下一个值则被他拿走
		}
		// 如果不有好几个元素,分别判断哪一边得到是分数高
		return Math.max(arr[i] + second(arr, i + 1, j),
				arr[j] + second(arr, i, j - 1));
	}

	//定义后拿人的函数(返回它的分数)
	private int second(int[] arr, int i, int j) {
		if (i == j) {
			return 0; // 如果只剩下一个值则被先拿的人拿走,它没有
		}
		// 如果还有多个牌,先拿的人会把最差的情况给后拿的,因此后拿的人
		return Math.min(frist(arr, i + 1, j), frist(arr, i, j - 1));
	}
}