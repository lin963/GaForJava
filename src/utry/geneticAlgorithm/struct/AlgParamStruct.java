package utry.geneticAlgorithm.struct;
/**
 * 算法参数
 * @author DFF
 *
 */
public class AlgParamStruct {
	//种群规模
	public int popSize;
	//最大迭代次数
	public int maxLoop;
	//交叉率
	public float pc;
	//变异率
	public float pm;
	//精英保存的数量
	public int nbest;
	//迭代次数超过其无进化，则算法终止
	public int maxUnchangedLoop;
	//用于判定两个浮点数近似程度
	public float error;
	//适应变换时使用的参数
	public float lambDa1;
	//适应变换时使用的参数
	public float lambda2;
	//适应变换时使用的参数
	public int fmax;
	//控制while循环次数
	public int maxCycleIndex;
	//当前代所有个体的平均值
	public float averageFitCurrentG;
	//当代最优个体的索引号
	public int maxPopIndex;
}
