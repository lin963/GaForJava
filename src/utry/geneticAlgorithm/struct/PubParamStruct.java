package utry.geneticAlgorithm.struct;
/**
 * 共用参数结构体
 * @author DFF
 *
 */

import java.util.Date;

import utry.util.datatable.DataTable;

public class PubParamStruct {

	//专业ID
	public String zhuanyeId;
	//区域代码,用来唯一识别排班项目,不是排班模块中所说的项目
	public String schAreaCode;
	//排班周期起始日
	public Date dateBegin;
	//排班周期结束日
	public Date dateEnd;
	//排班精度
	public int precison;
	//需求人力表
	public DataTable manPowerNeed;
	//系统排班起点
	public String csBeginPointString;
	//系统排班止点
	public String csEndPointString;
	//排班周期内的天数
	public int dmonth;
	//排班时间点个数
	public int btime;
	//每个技能每个群组的排班单位个数
	public int[][] nteam;
	//每个技能每个群组读取的上期班表的天数
	public int[][] dlast;
	//排班起始点，时间点的形式
	public int beginPointInt;
	//排班终止点，时间点的形式
	public int endPointInt;
	//是否考虑与上个周期的衔接
	public boolean whetherLinkupWithLastPeriod;
	//
	public int byType;
	//是否需要计算补钟班次
	public boolean whetherAddRest;
	//班次富裕率，只有班次富裕节点/班次覆盖节点>addRestProp才建议班次补钟
	public float addRestProp;
	//是否选择上6休2模式
	public boolean isOn6Off2;	
	
}
