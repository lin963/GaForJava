package utry.prd.empty;

import java.util.Random;
import java.util.UUID;

import utry.geneticAlgorithm.GaForJava;
import utry.prd.empty.datastruct.PbBanCi;
import utry.prd.empty.datastruct.PbParamStruct;
import utry.prd.empty.datastruct.PopStruct;
import utry.util.datatable.DataRow;
import utry.util.datatable.DataTable;

/**
 * 遗传算法生成空班表
 * @author DFF
 *
 */
public class GaPb2 {
	
	// 种群规模   说明：必须为正整数且为偶数
	private int popSize;
	// 算法中止条件  最大迭代次数   说明：正整数
	private int mostGeneration;
	//当代所有种群的平均适应度 
	private double averageFit;
	//排班参数结构体  （结构体变量）
	private PbParamStruct pbParam;
	//种群 结构体数组（存放结构体变量）
	private PopStruct[] pop;
	//交配池（用于选择、交叉、变异产生下一代种群）（存放结构体变量）
	private PopStruct[] crossMutationPool;
	// 当前运行代数
	private int currentGeneration;
	// 当代最优个体的索引号
	private int maxPopIndex;
	/**
	 * 构造方法
	 */
	public GaPb2() {
		super();
	}
	/**
	 * 遗传算法排班算法的类入口方法
	 * @param timePointNeedPerson 	//每个时间点所需人力		//类型:DataTable  字段:时间点(DPOINT),需求人数(NEEDPERSON),安排人数(SETPERSON) 
	 * @param banCiLibrary 			//班次库					//类型:DataTable  字段:班次描述(WORKDESCR),是否跳班(ISTiaoBan值为0或1，分别表示非跳班或跳班),安排人数(SETPERSON)
	 * @param workPersonNum			//工作人数				//类型:int  说明: 必须为正整数
	 * @param tiaoBanMaxNum			//跳班最大数				//类型:int  说明: 必须为正整数
	 * @param popSizeReal			//种群数量				//类型:int(默认20)  说明:必须为正整数且为偶数
	 * @param mostGenerationReal	//迭代次数				//类型:int(默认1000) 说明:必须为正整数
	 * @throws Exception 
	 */
	public void gaPbAlgorithm(DataTable timePointNeedPerson, DataTable banCiLibrary,int workPersonNum, int tiaoBanMaxNum, int popSizeReal, int mostGenerationReal) throws Exception{
		this.popSize=popSizeReal;
		if(popSize<=0||popSize%2 !=0){
			throw new Exception("抱歉！输入种群规模必须为正整数且为偶数，请重新确认数据！");
		}else{
			this.mostGeneration=mostGenerationReal;
			if(this.mostGeneration<=0){
				throw new Exception("抱歉！输入算法迭代次数必须为正整数，请重新确认数据！");
			}else{
				try{
					initialPbParam(timePointNeedPerson, banCiLibrary,workPersonNum, tiaoBanMaxNum);
				}catch(Exception e){
					throw e;
				}
				try{
					initialPopGene();
				}catch(Exception e){
					throw new Exception("种群初始化时出现异常");
				}
				try{
					gaEvolution(timePointNeedPerson,banCiLibrary);
				}catch(Exception e){
					throw e;
				}
			}
		}
	}
	/**
	 * 初始化排班
	 * @param timePointNeedPerson
	 * @param banCiLibrary
	 * @param workPersonNum
	 * @param tiaoBanMaxNum
	 * @throws Exception
	 * 1.判断 工作人数,跳班最大人数
	 * 2.确定所需人才
	 * 3.班次进行调整，有跳班的有前，没跳班的在后
	 */
	private void initialPbParam(DataTable timePointNeedPerson, DataTable banCiLibrary, int workPersonNum, int tiaoBanMaxNum) throws Exception{
		//获得一天的时间点数
		this.pbParam.pbTimePointNum=timePointNeedPerson.getEntityRows().size();
		//获得跳班上限
		this.pbParam.pbTiaoBanPersonMax=tiaoBanMaxNum;
		//获得一天上班人数
		this.pbParam.pbWorkPersonNum=workPersonNum;
		if(this.pbParam.pbWorkPersonNum<=0){
			throw new Exception("抱歉！上班总人数必须为正整数，请重新确认数据!");
		}else if(this.pbParam.pbTiaoBanPersonMax<0||this.pbParam.pbTiaoBanPersonMax>this.pbParam.pbWorkPersonNum){
			 throw new Exception("抱歉！跳班必须为非负整数并且小于或等于上班总人数，请重新确认数据!");
		}else{
			//获取时间点的需求人力数
            //获取每个时间点的需求人数时若存在字符数据等非法数字时，抛出异常
			try{
				this.pbParam.pbTimePointNeetPerson=new int[this.pbParam.pbTimePointNum];
				for(int i=0;i<timePointNeedPerson.getEntityRows().size();i++){
					this.pbParam.pbTimePointNeetPerson[i]=Integer.parseInt(timePointNeedPerson.getRow(i).getValue("NEEDPERSON").toString());
				}
			}catch(Exception e){
				throw new Exception("抱歉！获取每天每个时间点的需求人数时出现异常，可能存在不合法的非整型数据，请重新确认数据！");
			}
			//求每个时间点需求人数的平方之和
			int needPersonSqrSum=0;
			for(int j=0;j<pbParam.pbTimePointNum;j++){
				needPersonSqrSum+=this.pbParam.pbTimePointNeetPerson[j]*this.pbParam.pbTimePointNeetPerson[j];
			}
			this.pbParam.pbOneDayNeedPersonSqrSum=needPersonSqrSum;
			if(needPersonSqrSum==0){
				throw new Exception("抱歉！系统统计全天总需求单位为0，无意义，可能由于话务量与参排人员严重不匹配（话务量低），在需求人力转换成需求单位时，需求人力/平均单位人数<1，四舍五入为：0，请重新确认数据！");
			}else{
				///获取班次库
	            //获取班次库若出现不合法的字段时，抛出异常
				try{
					this.pbParam.banCi=new PbBanCi[banCiLibrary.getEntityRows().size()];
					this.pbParam.pbTiaoBanCiNum=0;
					int kk=0;
					//将班次库归类(全部跳班在前，非跳班在后)
					for(int i=0;i<banCiLibrary.getEntityRows().size();i++){
						if(banCiLibrary.getEntityRows().get(i).getValue("IsTiaoBan").toString()=="1"){
							this.pbParam.banCi[kk].description=banCiLibrary.getEntityRows().get(i).getValue("WORKDESC").toString();
							this.pbParam.banCi[kk].seriesId="1";
							this.pbParam.pbTiaoBanCiNum++;	//统计跳班次数的数量
							kk++;					
						}
					}
					int k=this.pbParam.pbTiaoBanCiNum;
					for(int i=0;i<banCiLibrary.getEntityRows().size();i++){
						if(banCiLibrary.getEntityRows().get(i).getValue("IsTiaoBan").toString()=="0"){
							this.pbParam.banCi[k].description=banCiLibrary.getEntityRows().get(i).getValue("WORKDESC").toString();
							this.pbParam.banCi[k].seriesId="0";
							k++;
						}					
					}				
				}catch(Exception e){
					throw new Exception("抱歉！获取班次时出现异常，可能班次库存在不合法的字段，请重新确认数据！");
				}
				//判断跳班班次数与跳班上限是否有冲突（当跳班班次数为0，而跳班上限非0时，被认为是矛盾），若有则抛出异常
				if(this.pbParam.pbTiaoBanCiNum==0&&this.pbParam.pbTiaoBanPersonMax!=0){
					throw new Exception("抱歉！所提供班次库中不存在跳班班次而试图安排跳班人力，请重新确认数据！");
				}else{
					//将班次描述分段存储,节点化
					for(int i=0;i<this.pbParam.banCi.length;i++){
						String[] banCiDuanString;
						String[][] banCiTimePointString;
						//捕捉班次格式异常
						banCiDuanString=this.pbParam.banCi[i].description.trim().split(" ");
						banCiTimePointString=new String[banCiDuanString.length][];
						for(int j=0;j<banCiDuanString.length;j++){
							if(banCiDuanString[j].indexOf("-")==5){
								String[] s=banCiDuanString[j].split("-");
								banCiTimePointString[j]=new String[s.length];
								for(int l=0;l<s.length;l++){
									banCiTimePointString[j][l]=s[l];
								}
							}else{
								throw new Exception("抱歉！目前系统只支持每段班以“-”隔开的00:00-00:00 00:00-00:00形式的若干段班次，请重新确认数据！");
							}						
						}
						try{
							//每个班次的班段的转化为时间点（如当排班精度为15时，08：00变为32）
							this.pbParam.banCi[i].banCiDuanPoint=new int[2*banCiDuanString.length];
							int kkk=0;
							for(int j=0;j<banCiDuanString.length;j++){
								for(int m=0;m<2;m++){
									this.pbParam.banCi[i].banCiDuanPoint[kkk]=banCiToTimePoint(banCiTimePointString[j][m]);
									kkk++;
								}
							}
						}catch(Exception e){
							throw e;
						}
						try{
							//将班次节点化，当某个班次安排人上班时，表示在某个时间点上该班次是否有人上班，1表示有人，0表无人
							this.pbParam.banCi[i].pbBanCiPoint=new int[this.pbParam.pbTimePointNum];
							//班次若为跨天班，则下一天的时间节点
							this.pbParam.banCi[i].pbBanCiPointNextD=new int[pbParam.pbTimePointNum];
							//每个班次的每个班段对应的时间点的值为1，不在班段范围内的时间点的值为0
							int kkj=0;
							while(kkj<2*banCiDuanString.length){
								//判断是否跨夜
								if(pbParam.banCi[i].banCiDuanPoint[0]>pbParam.banCi[i].banCiDuanPoint[2*banCiDuanString.length-1]){
									//跨夜
									//班段是否跨越24点
									if(pbParam.banCi[i].banCiDuanPoint[kkj]>pbParam.banCi[i].banCiDuanPoint[kkj+1]){
										for(int mm=pbParam.banCi[i].banCiDuanPoint[kkj];mm<GaForJava.pubParam.btime;mm++){
											pbParam.banCi[i].pbBanCiPoint[mm]=1;
										}
										for(int mm=0;mm<pbParam.banCi[i].banCiDuanPoint[kkj+1];mm++){
											pbParam.banCi[i].pbBanCiPointNextD[mm]=1;
										}
									}else{
										for(int mm=pbParam.banCi[i].banCiDuanPoint[kkj];mm<pbParam.banCi[i].banCiDuanPoint[kkj+1];mm++){
											if(mm>pbParam.banCi[i].banCiDuanPoint[2*banCiDuanString.length-1]){
												pbParam.banCi[i].pbBanCiPoint[mm]=1;
											}else{
												pbParam.banCi[i].pbBanCiPointNextD[mm]=1;
											}
										}
									}
								}else{
									//非跨夜
									for(int mm=pbParam.banCi[i].banCiDuanPoint[kkj];mm<pbParam.banCi[i].banCiDuanPoint[kkj+1];mm++){
										pbParam.banCi[i].pbBanCiPoint[mm]=1;
									}
								}
								kkj+=2;
							}
							//跨夜班从24点分开
							/*
							while(kkj<2*banCiDuanString.length){
								for(int mm=pbParam.banCi[i].banCiDuanPoint[kkj];mm<pbParam.banCi[i].banCiDuanPoint[kkj+1];mm++){
									if(kkj>0){
										//后一班段的开始时间要大于前一班段的结束时间
										if(mm>pbParam.banCi[i].banCiDuanPoint[kkj-1]){
											if(pbParam.banCi[i].wheCrossNight){
												pbParam.banCi[i].pbBanCiPointNextD[mm]=1;
											}else{
												pbParam.banCi[i].pbBanCiPoint[mm]=1;
											}
										}else{
											//否则即为跨夜班，需要将人力统计在下一天
											pbParam.banCi[i].wheCrossNight=true;
											pbParam.banCi[i].pbBanCiPointNextD[mm]=1;
										}
									}else{
										pbParam.banCi[i].pbBanCiPoint[mm]=1;
									}
									kkj+=2; 
								}
							}
							*/						
						}catch(Exception e){
							throw new Exception("抱歉！班次时间点转化时出现异常！");
						}					
					}
				}
			}
		}		
	}
	/**
	 * 初始化种群
	 */
	private void initialPopGene(){
		encode();
	}
	/**
	 * 遗传算法主程序(种群进化)
	 * @param timePointNeedPerson
	 * @param banCiLibraray
	 * @throws Exception 
	 */
	private void gaEvolution(DataTable timePointNeedPerson, DataTable banCiLibraray) throws Exception{
		//记录当前代数
		currentGeneration=0;
		
		crossMutationPool=new PopStruct[popSize];
		//初始化交配池
		startPopGeneData(crossMutationPool);
		while(currentGeneration<mostGeneration){
			try{
				evaluateFit();
			}catch(Exception e){
				throw new Exception("抱歉!评价适应度时出现异常!");
			}
			try{
				select();
			}catch(Exception e){
				throw new Exception("抱歉!种群选择时出现异常!");
			}
			try{
				cross();
			}catch(Exception e2){
				throw new Exception("抱歉!种群交叉时了现异常!");
			}
			try{
				mutation();
			}catch(Exception e3){
				throw new Exception("抱歉!种群变异时出现异常!");
			}
			try{
				update();
			}catch(Exception e4){
				throw new Exception("抱歉!种群更新时出现异常!");
			}
			currentGeneration++;
		}
		try{
			getCurrentDayBestFitnessAndPopChoice();
		}catch(Exception e){
			throw new Exception("抱歉!统计最后决策时出现异常!");
		}
		try{
			//更新两个Datatable供输出使用
			for(int i=0;i<timePointNeedPerson.getEntityRows().size();i++){
				//
				timePointNeedPerson.getEntityRows().get(i).setValue(2, pbParam.pbEachDayTimePointSetPerson[i]);
				//因为当天可能使用跨夜班导致下一天安排人力
				timePointNeedPerson.getEntityRows().get(i).setValue("NextDSetP", pbParam.pbNextDayTimePointSetPerson[i]);
			}
			//更新每个班次当前天的使用情况
			for(int i=0;i<pbParam.banCi.length;i++){
				DataRow findP=banCiLibraray.selectRow("WORKDESCR='"+pbParam.banCi[i].description+"'").get(0);
				findP.setValue("SETPERSON", pbParam.pbEachDayBestChoice[i]);
			}
		}catch(Exception e){
			throw new Exception("抱歉!数据表更新时出现异常!");
		}		
	}
	/**
	 * 评价适应度,得到当前代的最优个体及当代的平均适应度
	 */
	private void evaluateFit(){
		//求取最佳适应度的种群索引号,所有种群的适应度之和
		maxPopIndex=popSize-1;
		double sumFit=0.0;
		for(int i=0;i<pop.length;i++){
			sumFit+=pop[i].fit;
			if(pop[i].fit>pop[maxPopIndex].fit){
				maxPopIndex=i;
			}
		}
		//得到当前代的平均适应度
		averageFit=sumFit/popSize;
		//保存当前代适应度最佳值及其对应的基因
		if(pop[maxPopIndex].fit>pop[popSize].fit){
			pop[popSize].fit=pop[maxPopIndex].fit;
			for(int i=0;i<pop[popSize].popGeneData.length;i++){
				for(int j=0;j<pop[popSize].popGeneData[i].length;j++){
					pop[popSize].popGeneData[i][j]=pop[maxPopIndex].popGeneData[i][j];
				}
			}
		}
	}
	/**
	 * 选择算子
	 */
	private void select(){
		//适应度作转换
		double[] tempFit=new double[popSize];
		for(int i=0;i<popSize;i++){
			if(currentGeneration<=0.4*mostGeneration){
				tempFit[i]=Math.abs(pop[i].fit-averageFit);
			}else{
				tempFit[i]=Math.tan(currentGeneration/mostGeneration)*(Math.PI/4)*pop[i].fit+pop[popSize].fit;
			}
		}
		//按变换后的适应度求取每个种群的适应度选择概率
		double sumTempFit=0.0;
		for(int i=0;i<popSize;i++){
			sumTempFit=sumTempFit+tempFit[i];
		}
		double[] selectPosibility=new double[popSize];
		for(int i=0;i<popSize;i++){
			selectPosibility[i]=tempFit[i]/sumTempFit;
		}
		int newPopNum=0;
		Random random=new Random();
		//轮盘赌选择开始
		while(newPopNum<popSize){
			float pick=random.nextFloat();
			double currentSum=0;
			int i=0;
			while(currentSum<pick){
				currentSum=currentSum+selectPosibility[i];
				i++;
			}
			//将被选择出来的个体放入交配池
			for(int p=0;p<pop[i-1].popGeneData.length;p++){
				for(int q=0;q<pop[i-1].popGeneData[p].length;q++){
					crossMutationPool[newPopNum].popGeneData[p][q]=pop[i-1].popGeneData[p][q];
				}
			}
			crossMutationPool[newPopNum].fit=pop[i-1].fit;
			//选择下一个体
			newPopNum++;
		}
	}
	/**
	 * 交叉算子
	 */
	private void cross(){
		int[] w=new int[pbParam.pbWorkPersonNum];
		for(int i=0;i<pbParam.pbWorkPersonNum;i++){
			//用于均匀交叉,判断是否交换对应位置上的数字
			w[i]=randbit(0,2,1);
		}
		//用于存放每次作交叉操作后的四个个体(父代2+子代2)
		PopStruct[] tempFour=new PopStruct[4];
		//定义基因数组
		startPopGeneData(tempFour);
		//用于存放每次发生交叉操作后的2个子代个体
		PopStruct[] tempTwo=new PopStruct[2];
		//定义基因数组
		startPopGeneData(tempTwo);
		for(int k=0;k<popSize;){
			Random random=new Random();
			int numIndex=0;
			boolean change=false;
			//计算当前两个待交叉的个体的交叉率
			double crossP=0.0;
			double moreFit=Math.max(crossMutationPool[k].fit, crossMutationPool[k+1].fit);
			if(moreFit>=averageFit){
				crossP=0.9-0.5*(moreFit-averageFit)/(pop[maxPopIndex].fit-averageFit);
			}else{
				crossP=0.9;
			}
			//转存待交叉的两个染色体
			for(int i=0;i<crossMutationPool[k].popGeneData.length;i++){
				for(int j=0;j<crossMutationPool[k].popGeneData[i].length;j++){
					tempTwo[0].popGeneData[i][j]=crossMutationPool[k].popGeneData[i][j];
					tempTwo[1].popGeneData[i][j]=crossMutationPool[k+1].popGeneData[i][j];
				}
			}
			//开始交叉
			tempTwo[0].fit=crossMutationPool[k].fit;
			tempTwo[1].fit=crossMutationPool[k+1].fit;
			for(int p=0;p<crossMutationPool[k].popGeneData.length;p++){
				for(int q=0;q<crossMutationPool[k].popGeneData[p].length;q++){
					numIndex++;
					float pick=random.nextFloat();
					if(pick<crossP){
						if(w[numIndex-1]==1){
							int temp=tempTwo[0].popGeneData[p][q];
							tempTwo[0].popGeneData[p][q]=tempTwo[1].popGeneData[p][q];
							tempTwo[1].popGeneData[p][q]=temp;
							change=true;
						}
					}
				}
			}
			//若发生交叉,则通过竞争选择从两个父代和两个子代中选取两个最优的个体作为下一代种群个体
			if(change){
				tempTwo[0].fit=calCulateFitness(tempTwo[0].popGeneData);
				tempTwo[1].fit=calCulateFitness(tempTwo[1].popGeneData);
				//汇总四个个体,取经竞争后最优的两个个体作为下一代种群个休
				for(int p=0;p<crossMutationPool[k].popGeneData.length;p++){
					for(int q=0;q<crossMutationPool[k].popGeneData[p].length;q++){
						tempFour[0].popGeneData[p][q]=tempTwo[0].popGeneData[p][q];
						tempFour[1].popGeneData[p][q]=tempTwo[1].popGeneData[p][q];
						tempFour[2].popGeneData[p][q]=crossMutationPool[k].popGeneData[p][q];
						tempFour[3].popGeneData[p][q]=crossMutationPool[k+1].popGeneData[p][q];
					}
				}
				tempFour[0].fit=tempTwo[0].fit;
				tempFour[1].fit=tempTwo[1].fit;
				tempFour[2].fit=crossMutationPool[k].fit;
				tempFour[3].fit=crossMutationPool[k+1].fit;
				//将四个个体根据适应度由大到小排序
				paiXu(tempFour, tempFour.length);
				//取两个最优的作为下一代的个体
				for(int p=0;p<crossMutationPool[k].popGeneData.length;p++){
					for(int q=0;q<crossMutationPool[k].popGeneData[p].length;q++){
						crossMutationPool[k].popGeneData[p][q]=tempFour[0].popGeneData[p][q];
						crossMutationPool[k+1].popGeneData[p][q]=tempFour[1].popGeneData[p][q];
					}
				}
				crossMutationPool[k].fit=tempFour[0].fit;
				crossMutationPool[k+1].fit=tempFour[1].fit;
			}
			//选择下一对交叉的个体
			k+=2;
		}
	}
	/**
	 * 变异算子
	 */
	private void mutation(){
		int[] w=new int[pbParam.pbWorkPersonNum];
		for(int i=0;i<pbParam.pbWorkPersonNum;i++){
			//用于变异,选择变异基因值的范围
			w[i]=randbit(0,2,i);
		}
		for(int i=0;i<popSize;i++){
			Random random=new Random();
			//标记个体是否发生变异
			boolean change=false;
			//计算当前个体的变异率
			double mutationP=0;
			if(crossMutationPool[i].fit>=averageFit){
				mutationP=0.1-0.05*(crossMutationPool[i].fit-averageFit)/(pop[maxPopIndex].fit-averageFit);
			}else{
				mutationP=0.1;
			}
			//均匀变异
			int index=0;
			for(int j=0;j<crossMutationPool[i].popGeneData.length;j++){
				for(int k=0;k<crossMutationPool[i].popGeneData[j].length;k++){
					index++;
					float pick=random.nextFloat();
					if(pick<mutationP){
						if(j==0){
							if(w[index-1]==0){
								crossMutationPool[i].popGeneData[j][k]=randbit(0,pbParam.pbTiaoBanCiNum,k);
							}else{
								crossMutationPool[i].popGeneData[j][k]=crossMutationPool[i].popGeneData[j][randbit(0,crossMutationPool[i].popGeneData[j].length,k)];
							}
							change=true;
						}
					}else{
						if(w[index-1]==0){
							crossMutationPool[i].popGeneData[j][k]=randbit(pbParam.pbTiaoBanCiNum,pbParam.banCi.length,k);
						}else{
							crossMutationPool[i].popGeneData[j][k]=crossMutationPool[i].popGeneData[j][randbit(0,crossMutationPool[i].popGeneData[j].length,k)];
						}
						change=true;
					}
				}
				//若发生变异,计算变异后个体的适应度,若未,则直接对下一个个体进行变异
				if(change){
					crossMutationPool[i].fit=calCulateFitness(crossMutationPool[i].popGeneData);
				}
			}
		}
	}
	/**
	 * 种群更新即产生下一代种群
	 */
	private void update(){
		//对上一代种群按适应度由大到小排序
		paiXu(pop,popSize);
		//对交叉变异后的个体按适应度由大到小排序
		paiXu(crossMutationPool,popSize);
		int j=crossMutationPool.length-1;
		//将上一代中大于交叉变异后种群中最大适应度的所有个体取代当代中相等数据的最差个体
		//当上代的最优个体大于此代的最优个体时,精英保存操作后再更新种群,否则不需要取代直接更新总群
		if(pop[0].fit>crossMutationPool[0].fit){
			//清英保存
			for(int i=0;i<pop.length-1;i++){
				if(pop[i].fit>crossMutationPool[0].fit){
					for(int p=0;p<pop[i].popGeneData.length;p++){
						for(int q=0;q<pop[i].popGeneData[p].length;q++){
							crossMutationPool[j].popGeneData[p][q]=pop[i].popGeneData[p][q];
						}
					}
					crossMutationPool[j].fit=pop[i].fit;
					j--;
				}else{
					break;
				}
			}
			//更新种群
			for(int i=0;i<pop.length-1;i++){
				for(int p=0;p<pop[i].popGeneData[p].length;p++){
					for(int q=0;q<pop[i].popGeneData[p].length;q++){
						pop[i].popGeneData[p][q]=crossMutationPool[i].popGeneData[p][q];
					}
				}
				pop[i].fit=crossMutationPool[i].fit;
			}
		}else{
			//不需要作精英保存,而直接更新产生下一代种群
			//更新种群
			for(int i=0;i<pop.length-1;i++){
				for(int p=0;p<pop[i].popGeneData.length;i++){
					for(int q=0;q<pop[i].popGeneData[p].length;q++){
						pop[i].popGeneData[p][q]=crossMutationPool[i].popGeneData[p][q];
					}
				}
				pop[i].fit=crossMutationPool[i].fit;
			}
		}
	}
	/**
	 * 得到该天的最后决策,该天每个时间点设置的上班人数
	 */
	private void getCurrentDayBestFitnessAndPopChoice(){
		//存放该天的最后决策
		pbParam.pbEachDayBestChoice=new int[pbParam.banCi.length];
		//将最优基因解码得到每个班次安排的上班人数
		for(int i=0;i<pop[popSize].popGeneData.length;i++){
			for(int j=0;j<pop[popSize].popGeneData[i].length;j++){
				pbParam.pbEachDayBestChoice[pop[popSize].popGeneData[i][j]]+=1;
			}
		}
		//计算该天每个时间点的实际安排人数
		pbParam.pbEachDayTimePointSetPerson=new int[pbParam.pbTimePointNum];
		for(int j=0;j<pbParam.pbTimePointNum;j++){
			for(int k=0;k<pbParam.banCi.length;k++){
				if(pbParam.pbEachDayBestChoice[k]>0&&pbParam.banCi[k].pbBanCiPoint[j]>0){
					pbParam.pbEachDayTimePointSetPerson[j]=pbParam.pbEachDayTimePointSetPerson[j]+pbParam.pbEachDayBestChoice[k]*pbParam.banCi[k].pbBanCiPoint[j];
				}				
			}
		}
		//计算该天若使用了跨夜班对下一天每个时间点的产生的人力安排
		pbParam.pbNextDayTimePointSetPerson=new int[pbParam.pbTimePointNum];
		for(int j=0;j<pbParam.pbTimePointNum;j++){
			for(int k=0;k<pbParam.banCi.length;k++){
				if(pbParam.pbEachDayBestChoice[k]>0&&pbParam.banCi[k].pbBanCiPointNextD[j]>0){
					pbParam.pbNextDayTimePointSetPerson[j]=pbParam.pbNextDayTimePointSetPerson[j]+pbParam.pbEachDayBestChoice[k]*pbParam.banCi[k].pbBanCiPointNextD[j];
				}
			}
		}
	}
	/**
	 * 班次转换，将23:55转换成具体的数字班表:15分种为96，30分种为:48
	 * @param s
	 * @return
	 */
	private int banCiToTimePoint(String s){
		//s="23:55"
		int hour=Integer.parseInt(s.split(":")[0].toString());
		int minus=Integer.parseInt(s.split(":")[1].toString());
		int iInt = (hour*60+minus)*this.pbParam.pbTimePointNum/1440;
        if (iInt > pbParam.pbTimePointNum){
        	iInt = pbParam.pbTimePointNum; 
        }
        return iInt;
	}
	
	/**
	 * 编码:
	 */
	private void encode(){
		this.pop=new PopStruct[this.popSize+1];
		//定义个体的基因数组及初始化个体适应度
		startPopGeneData(this.pop);
		for(int i=0;i<popSize;i++){
			for(int j=0;j<pop[i].popGeneData.length;j++){
				//针对两类班(跳班与非跳班),注意在排班参数初始化时已经将班次归类，所有跳班在前，非跳班在后
				for(int k=0;k<pop[i].popGeneData[j].length;k++){
					if(j==0){
						//对上跳班的每个人进行班次号编码
						pop[i].popGeneData[j][k]=randbit(0,pbParam.pbTiaoBanCiNum,k);
					}else{
						//对上非跳班的每个人进行班次号编码
						pop[i].popGeneData[j][k]=randbit(pbParam.pbTiaoBanCiNum,pbParam.banCi.length,k);
					}
				}	
			}
			//计算该个体的适应度
			pop[i].fit=calCulateFitness(pop[i].popGeneData);
		}
	}
	/**
	 * 定义个体基因数组并初始化个体适应度
	 * @param pop
	 */
	private void startPopGeneData(PopStruct[] pop){
		for(int i=0;i<pop.length;i++){
			//存放每个种群个体的基因
			pop[i].popGeneData=new int[2][];
			//跳班达到最大约束，且保证上班人数达到要求
			pop[i].popGeneData[0]=new int[pbParam.pbTiaoBanPersonMax];
			
			pop[i].popGeneData[1]=new int[pbParam.pbWorkPersonNum-pbParam.pbTiaoBanPersonMax];
			pop[i].fit=0;
		}
	}
	/**
	 * 计算种群个体适应度
	 * @param popGeneData
	 * @return
	 */
	public double calCulateFitness(int[][] popGeneData){
		double similar=0.0;
		//对染色体解码，即统计每个班次安排工作人数
		int[] popDecodeChoice=new int[pbParam.banCi.length];
		for(int j=0;j<popGeneData.length;j++){
			for(int k=0;k<popGeneData[j].length;k++){
				//统计每个班次上班的总人数
				popDecodeChoice[popGeneData[j][k]]+=1;
			}
		}
		//求拟合度
		int deltaSetNeedofDay=0;
		for(int j=0;j<pbParam.pbTimePointNum;j++){
			int setPersonofTimePoint=0;
			for(int k=0;k<pbParam.banCi.length;k++){
				//佛山太保夜班拟合时，修改setPersonofTimePoint计算方式
				//判断是否跨夜
				if(pbParam.banCi[k].banCiDuanPoint[0]>pbParam.banCi[k].banCiDuanPoint.length-1){
					if(popDecodeChoice[k]>0&&pbParam.banCi[k].pbBanCiPointNextD[j]>0){
						setPersonofTimePoint=setPersonofTimePoint+popDecodeChoice[k]*pbParam.banCi[k].pbBanCiPointNextD[j];
					}
				}else{
					if(popDecodeChoice[k]>0&&pbParam.banCi[k].pbBanCiPoint[j]>0){
						setPersonofTimePoint=setPersonofTimePoint+popDecodeChoice[k]*pbParam.banCi[k].pbBanCiPoint[j];
					}
				}
			}
			if(pbParam.banCi[0].banCiDuanPoint[0]>pbParam.banCi[0].banCiDuanPoint[pbParam.banCi[0].banCiDuanPoint.length-1]&&j<6*60/GaForJava.pubParam.precison){
				if(pbParam.pbTimePointNeetPerson[j]!=0&&setPersonofTimePoint==0){
					//当需求人数非0而设置人数为0时,引入惩罚因子
					deltaSetNeedofDay=deltaSetNeedofDay+2000*pbParam.pbOneDayNeedPersonSqrSum*pbParam.pbTimePointNeetPerson[j]/pbParam.pbTimePointNum;
				}else if(pbParam.pbTimePointNeetPerson[j]==0&&setPersonofTimePoint!=0){
					//当需求人数为0而设置人数非0时,引入惩罚因子
					deltaSetNeedofDay=deltaSetNeedofDay+setPersonofTimePoint*pbParam.pbOneDayNeedPersonSqrSum/pbParam.pbTimePointNum;					
				}else{
					if(j<6*60/GaForJava.pubParam.precison&&(setPersonofTimePoint-pbParam.pbTimePointNeetPerson[j]<0)){
						deltaSetNeedofDay=deltaSetNeedofDay+1000*(int)Math.pow((setPersonofTimePoint-pbParam.pbTimePointNeetPerson[j]), 2);
					}else{
						deltaSetNeedofDay=deltaSetNeedofDay+(int)Math.pow((setPersonofTimePoint-pbParam.pbTimePointNeetPerson[j]), 2);
					}
				}
			}else{
				if(pbParam.pbTimePointNeetPerson[j]!=0&&setPersonofTimePoint==0){
					//当需求人数非0而设置人数为0时,引入惩罚因子
					deltaSetNeedofDay=deltaSetNeedofDay+200*pbParam.pbOneDayNeedPersonSqrSum*pbParam.pbTimePointNeetPerson[j]/pbParam.pbTimePointNum;
				}else if(pbParam.pbTimePointNeetPerson[j]==0&&setPersonofTimePoint!=0){
					//当需求人数为0而设置人数非0时,引入惩罚因子
					deltaSetNeedofDay=deltaSetNeedofDay+setPersonofTimePoint*pbParam.pbOneDayNeedPersonSqrSum/pbParam.pbTimePointNum;					
				}else{
					if(j<6*60/GaForJava.pubParam.precison&&(setPersonofTimePoint-pbParam.pbTimePointNeetPerson[j]<0)){
						deltaSetNeedofDay=deltaSetNeedofDay+100*(int)Math.pow((setPersonofTimePoint-pbParam.pbTimePointNeetPerson[j]), 2);
					}else{
						deltaSetNeedofDay=deltaSetNeedofDay+(int)Math.pow((setPersonofTimePoint-pbParam.pbTimePointNeetPerson[j]), 2);
					}
				}
			}
		}
		//在有些项目中，当人员超级富裕时，导致每个个体适应度函数都为负值，故将适应度函数如下转换
		/*
		if(deltaSetNeedofDay>pbParam.pbOneDayNeedPersonSqrSum){
			similar=0;
		}else{
			similar=100*(1-(deltaSetNeedofDay)/pbParam.pbOneDayNeedPersonSqrSum);
		}
		*/
		similar=100000000 + 100 * (1 - (deltaSetNeedofDay) / pbParam.pbOneDayNeedPersonSqrSum);

		return similar;
	}
	/**
	 * 随机函数
	 * @param start
	 * @param end
	 * @param p
	 * @return
	 */
	private int randbit(int start, int end, int p){
		int ret;
		Random random=new Random(UUID.randomUUID().hashCode()+p);
		ret=start+random.nextInt(end);
		return ret;
	}
	/**
	 * 排序的方法
	 * @param pop
	 * @param len
	 */
	private void paiXu(PopStruct[] pop, int len){
		for(int i=0;i<len-1;i++){
			for(int j=0;j<len-1-i;j++){
				if(pop[j].fit<pop[j+1].fit){
					PopStruct temp=new PopStruct();;
					temp=pop[j];
					pop[j]=pop[j+1];
					pop[j+1]=temp;
				}
			}
		}
	}
	/*此方法是属性不会代码的的编写的,
	private void paiXu(PopStruct[] pop, int len){
		for(int i=0;i<len-1;i++){
			for(int j=0;j<len-1-i;j++){
				if(pop[j].fit<pop[j+1].fit){
					PopStruct temp=new PopStruct();;
					temp.popGeneData=new int[2][];
					temp.popGeneData[0]=new int[pbParam.pbTiaoBanPersonMax];
					temp.popGeneData[1]=new int[pbParam.pbWorkPersonNum-pbParam.pbTiaoBanPersonMax];
					for(int p=0;p<pop[i].popGeneData.length;p++){
						for(int q=0;q<pop[i].popGeneData[p].length;q++){
							temp.popGeneData[p][q]=pop[j].popGeneData[p][q];
							pop[j].popGeneData[p][q]=pop[j+1].popGeneData[p][q];
							pop[j+1].popGeneData[p][q]=temp.popGeneData[p][q];
						}
					}
					temp.fit=pop[j].fit;
					pop[j].fit=pop[j+1].fit;
					pop[j+1].fit=temp.fit;
					
				}
			}
		}
	}
	*/
}
