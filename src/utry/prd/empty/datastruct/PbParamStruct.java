package utry.prd.empty.datastruct;
/**
 * 排班参数设置
 * @author DFF
 *
 */
public class PbParamStruct {
	//一天时间点的总数(由排班精度决定，当精度为15时，其值为96)
	public int pbTimePointNum;
	// 每个时间点的需求人力数(当排班精度为30时，维数为：48)
	public int[] pbTimePointNeetPerson;
	//一天每个点需求人数的平方之和
	public int pbOneDayNeedPersonSqrSum;
	// 每个时间点的最佳设置人数(当排班精度为30时，维数为:48)
	public int[] pbEachDayTimePointSetPerson;
	// 班次(结构体变量)
	public PbBanCi[] banCi;
	// 每天最好的基因决策  最后每天的对每个班次的决策
	public int[] pbEachDayBestChoice;
	// 上调班人数的上限
	public int pbTiaoBanPersonMax;
	// 调班班次数量
	public int pbTiaoBanCiNum;
	// 某天工作的人数
	public int pbWorkPersonNum;
	 //下一天每个时间点的安排人数
	public int[] pbNextDayTimePointSetPerson;
	
}
