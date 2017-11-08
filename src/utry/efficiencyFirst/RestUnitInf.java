package utry.efficiencyFirst;
/**
 * 每个排班单位的详细信息,包含:所属群组及群组下的排班单位序号
 * @author DFF
 *
 */
public class RestUnitInf {
	//排班单位对应的群组序号
	public int clusterXuHao;
	//排班单位对应的群组id下的排班单位序号
	public int unitXuHao;
	//排班单位所上群组下班次编号,比如10001
	public int banCi;
	//群组下的班次编号对应技能下的班次号
	public int ni;
}
