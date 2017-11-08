package utry.prd.empty.datastruct;
/**
 * 班次结构
 * @author DFF
 *
 */

public class PbBanCi {
	//班次的时间点(可以任意段的班次)
	public int[] banCiDuanPoint;
	//班次节点化(当排班精度为30时，维数为:每天时间点数(如48,由排班精度决定)，其值为0或1)
	public int[] pbBanCiPoint;
	//班次类别(非跳班或跳班等)值为(0或1)
	public String seriesId;
	//班次类别:早班或中班或晚班
	public String seriesLb;
	//班次描述(08:00-11:30	13:30-17:30)
	public String description;
	// 标记班次是否跨夜
	public boolean wheCrossNight;
	//(跨夜班第二天)班次节点化(当排班精度为30时，维数为：每天的时间点数（如48，由排班精度决定），其值为0或1
	public int[] pbBanCiPointNextD;
}
