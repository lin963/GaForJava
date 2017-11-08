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
 * �Ŵ��㷨���ɿհ��
 * @author DFF
 *
 */
public class GaPb2 {
	
	// ��Ⱥ��ģ   ˵��������Ϊ��������Ϊż��
	private int popSize;
	// �㷨��ֹ����  ����������   ˵����������
	private int mostGeneration;
	//����������Ⱥ��ƽ����Ӧ�� 
	private double averageFit;
	//�Ű�����ṹ��  ���ṹ�������
	private PbParamStruct pbParam;
	//��Ⱥ �ṹ�����飨��Žṹ�������
	private PopStruct[] pop;
	//����أ�����ѡ�񡢽��桢���������һ����Ⱥ������Žṹ�������
	private PopStruct[] crossMutationPool;
	// ��ǰ���д���
	private int currentGeneration;
	// �������Ÿ����������
	private int maxPopIndex;
	/**
	 * ���췽��
	 */
	public GaPb2() {
		super();
	}
	/**
	 * �Ŵ��㷨�Ű��㷨������ڷ���
	 * @param timePointNeedPerson 	//ÿ��ʱ�����������		//����:DataTable  �ֶ�:ʱ���(DPOINT),��������(NEEDPERSON),��������(SETPERSON) 
	 * @param banCiLibrary 			//��ο�					//����:DataTable  �ֶ�:�������(WORKDESCR),�Ƿ�����(ISTiaoBanֵΪ0��1���ֱ��ʾ�����������),��������(SETPERSON)
	 * @param workPersonNum			//��������				//����:int  ˵��: ����Ϊ������
	 * @param tiaoBanMaxNum			//���������				//����:int  ˵��: ����Ϊ������
	 * @param popSizeReal			//��Ⱥ����				//����:int(Ĭ��20)  ˵��:����Ϊ��������Ϊż��
	 * @param mostGenerationReal	//��������				//����:int(Ĭ��1000) ˵��:����Ϊ������
	 * @throws Exception 
	 */
	public void gaPbAlgorithm(DataTable timePointNeedPerson, DataTable banCiLibrary,int workPersonNum, int tiaoBanMaxNum, int popSizeReal, int mostGenerationReal) throws Exception{
		this.popSize=popSizeReal;
		if(popSize<=0||popSize%2 !=0){
			throw new Exception("��Ǹ��������Ⱥ��ģ����Ϊ��������Ϊż����������ȷ�����ݣ�");
		}else{
			this.mostGeneration=mostGenerationReal;
			if(this.mostGeneration<=0){
				throw new Exception("��Ǹ�������㷨������������Ϊ��������������ȷ�����ݣ�");
			}else{
				try{
					initialPbParam(timePointNeedPerson, banCiLibrary,workPersonNum, tiaoBanMaxNum);
				}catch(Exception e){
					throw e;
				}
				try{
					initialPopGene();
				}catch(Exception e){
					throw new Exception("��Ⱥ��ʼ��ʱ�����쳣");
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
	 * ��ʼ���Ű�
	 * @param timePointNeedPerson
	 * @param banCiLibrary
	 * @param workPersonNum
	 * @param tiaoBanMaxNum
	 * @throws Exception
	 * 1.�ж� ��������,�����������
	 * 2.ȷ�������˲�
	 * 3.��ν��е��������������ǰ��û������ں�
	 */
	private void initialPbParam(DataTable timePointNeedPerson, DataTable banCiLibrary, int workPersonNum, int tiaoBanMaxNum) throws Exception{
		//���һ���ʱ�����
		this.pbParam.pbTimePointNum=timePointNeedPerson.getEntityRows().size();
		//�����������
		this.pbParam.pbTiaoBanPersonMax=tiaoBanMaxNum;
		//���һ���ϰ�����
		this.pbParam.pbWorkPersonNum=workPersonNum;
		if(this.pbParam.pbWorkPersonNum<=0){
			throw new Exception("��Ǹ���ϰ�����������Ϊ��������������ȷ������!");
		}else if(this.pbParam.pbTiaoBanPersonMax<0||this.pbParam.pbTiaoBanPersonMax>this.pbParam.pbWorkPersonNum){
			 throw new Exception("��Ǹ���������Ϊ�Ǹ���������С�ڻ�����ϰ���������������ȷ������!");
		}else{
			//��ȡʱ��������������
            //��ȡÿ��ʱ������������ʱ�������ַ����ݵȷǷ�����ʱ���׳��쳣
			try{
				this.pbParam.pbTimePointNeetPerson=new int[this.pbParam.pbTimePointNum];
				for(int i=0;i<timePointNeedPerson.getEntityRows().size();i++){
					this.pbParam.pbTimePointNeetPerson[i]=Integer.parseInt(timePointNeedPerson.getRow(i).getValue("NEEDPERSON").toString());
				}
			}catch(Exception e){
				throw new Exception("��Ǹ����ȡÿ��ÿ��ʱ������������ʱ�����쳣�����ܴ��ڲ��Ϸ��ķ��������ݣ�������ȷ�����ݣ�");
			}
			//��ÿ��ʱ�������������ƽ��֮��
			int needPersonSqrSum=0;
			for(int j=0;j<pbParam.pbTimePointNum;j++){
				needPersonSqrSum+=this.pbParam.pbTimePointNeetPerson[j]*this.pbParam.pbTimePointNeetPerson[j];
			}
			this.pbParam.pbOneDayNeedPersonSqrSum=needPersonSqrSum;
			if(needPersonSqrSum==0){
				throw new Exception("��Ǹ��ϵͳͳ��ȫ��������λΪ0�������壬�������ڻ������������Ա���ز�ƥ�䣨�������ͣ�������������ת��������λʱ����������/ƽ����λ����<1����������Ϊ��0��������ȷ�����ݣ�");
			}else{
				///��ȡ��ο�
	            //��ȡ��ο������ֲ��Ϸ����ֶ�ʱ���׳��쳣
				try{
					this.pbParam.banCi=new PbBanCi[banCiLibrary.getEntityRows().size()];
					this.pbParam.pbTiaoBanCiNum=0;
					int kk=0;
					//����ο����(ȫ��������ǰ���������ں�)
					for(int i=0;i<banCiLibrary.getEntityRows().size();i++){
						if(banCiLibrary.getEntityRows().get(i).getValue("IsTiaoBan").toString()=="1"){
							this.pbParam.banCi[kk].description=banCiLibrary.getEntityRows().get(i).getValue("WORKDESC").toString();
							this.pbParam.banCi[kk].seriesId="1";
							this.pbParam.pbTiaoBanCiNum++;	//ͳ���������������
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
					throw new Exception("��Ǹ����ȡ���ʱ�����쳣�����ܰ�ο���ڲ��Ϸ����ֶΣ�������ȷ�����ݣ�");
				}
				//�ж��������������������Ƿ��г�ͻ������������Ϊ0�����������޷�0ʱ������Ϊ��ì�ܣ����������׳��쳣
				if(this.pbParam.pbTiaoBanCiNum==0&&this.pbParam.pbTiaoBanPersonMax!=0){
					throw new Exception("��Ǹ�����ṩ��ο��в����������ζ���ͼ��������������������ȷ�����ݣ�");
				}else{
					//����������ֶδ洢,�ڵ㻯
					for(int i=0;i<this.pbParam.banCi.length;i++){
						String[] banCiDuanString;
						String[][] banCiTimePointString;
						//��׽��θ�ʽ�쳣
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
								throw new Exception("��Ǹ��Ŀǰϵͳֻ֧��ÿ�ΰ��ԡ�-��������00:00-00:00 00:00-00:00��ʽ�����ɶΰ�Σ�������ȷ�����ݣ�");
							}						
						}
						try{
							//ÿ����εİ�ε�ת��Ϊʱ��㣨�統�Űྫ��Ϊ15ʱ��08��00��Ϊ32��
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
							//����νڵ㻯����ĳ����ΰ������ϰ�ʱ����ʾ��ĳ��ʱ����ϸð���Ƿ������ϰ࣬1��ʾ���ˣ�0������
							this.pbParam.banCi[i].pbBanCiPoint=new int[this.pbParam.pbTimePointNum];
							//�����Ϊ����࣬����һ���ʱ��ڵ�
							this.pbParam.banCi[i].pbBanCiPointNextD=new int[pbParam.pbTimePointNum];
							//ÿ����ε�ÿ����ζ�Ӧ��ʱ����ֵΪ1�����ڰ�η�Χ�ڵ�ʱ����ֵΪ0
							int kkj=0;
							while(kkj<2*banCiDuanString.length){
								//�ж��Ƿ��ҹ
								if(pbParam.banCi[i].banCiDuanPoint[0]>pbParam.banCi[i].banCiDuanPoint[2*banCiDuanString.length-1]){
									//��ҹ
									//����Ƿ��Խ24��
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
									//�ǿ�ҹ
									for(int mm=pbParam.banCi[i].banCiDuanPoint[kkj];mm<pbParam.banCi[i].banCiDuanPoint[kkj+1];mm++){
										pbParam.banCi[i].pbBanCiPoint[mm]=1;
									}
								}
								kkj+=2;
							}
							//��ҹ���24��ֿ�
							/*
							while(kkj<2*banCiDuanString.length){
								for(int mm=pbParam.banCi[i].banCiDuanPoint[kkj];mm<pbParam.banCi[i].banCiDuanPoint[kkj+1];mm++){
									if(kkj>0){
										//��һ��εĿ�ʼʱ��Ҫ����ǰһ��εĽ���ʱ��
										if(mm>pbParam.banCi[i].banCiDuanPoint[kkj-1]){
											if(pbParam.banCi[i].wheCrossNight){
												pbParam.banCi[i].pbBanCiPointNextD[mm]=1;
											}else{
												pbParam.banCi[i].pbBanCiPoint[mm]=1;
											}
										}else{
											//����Ϊ��ҹ�࣬��Ҫ������ͳ������һ��
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
							throw new Exception("��Ǹ�����ʱ���ת��ʱ�����쳣��");
						}					
					}
				}
			}
		}		
	}
	/**
	 * ��ʼ����Ⱥ
	 */
	private void initialPopGene(){
		encode();
	}
	/**
	 * �Ŵ��㷨������(��Ⱥ����)
	 * @param timePointNeedPerson
	 * @param banCiLibraray
	 * @throws Exception 
	 */
	private void gaEvolution(DataTable timePointNeedPerson, DataTable banCiLibraray) throws Exception{
		//��¼��ǰ����
		currentGeneration=0;
		
		crossMutationPool=new PopStruct[popSize];
		//��ʼ�������
		startPopGeneData(crossMutationPool);
		while(currentGeneration<mostGeneration){
			try{
				evaluateFit();
			}catch(Exception e){
				throw new Exception("��Ǹ!������Ӧ��ʱ�����쳣!");
			}
			try{
				select();
			}catch(Exception e){
				throw new Exception("��Ǹ!��Ⱥѡ��ʱ�����쳣!");
			}
			try{
				cross();
			}catch(Exception e2){
				throw new Exception("��Ǹ!��Ⱥ����ʱ�����쳣!");
			}
			try{
				mutation();
			}catch(Exception e3){
				throw new Exception("��Ǹ!��Ⱥ����ʱ�����쳣!");
			}
			try{
				update();
			}catch(Exception e4){
				throw new Exception("��Ǹ!��Ⱥ����ʱ�����쳣!");
			}
			currentGeneration++;
		}
		try{
			getCurrentDayBestFitnessAndPopChoice();
		}catch(Exception e){
			throw new Exception("��Ǹ!ͳ��������ʱ�����쳣!");
		}
		try{
			//��������Datatable�����ʹ��
			for(int i=0;i<timePointNeedPerson.getEntityRows().size();i++){
				//
				timePointNeedPerson.getEntityRows().get(i).setValue(2, pbParam.pbEachDayTimePointSetPerson[i]);
				//��Ϊ�������ʹ�ÿ�ҹ�ർ����һ�찲������
				timePointNeedPerson.getEntityRows().get(i).setValue("NextDSetP", pbParam.pbNextDayTimePointSetPerson[i]);
			}
			//����ÿ����ε�ǰ���ʹ�����
			for(int i=0;i<pbParam.banCi.length;i++){
				DataRow findP=banCiLibraray.selectRow("WORKDESCR='"+pbParam.banCi[i].description+"'").get(0);
				findP.setValue("SETPERSON", pbParam.pbEachDayBestChoice[i]);
			}
		}catch(Exception e){
			throw new Exception("��Ǹ!���ݱ����ʱ�����쳣!");
		}		
	}
	/**
	 * ������Ӧ��,�õ���ǰ�������Ÿ��弰������ƽ����Ӧ��
	 */
	private void evaluateFit(){
		//��ȡ�����Ӧ�ȵ���Ⱥ������,������Ⱥ����Ӧ��֮��
		maxPopIndex=popSize-1;
		double sumFit=0.0;
		for(int i=0;i<pop.length;i++){
			sumFit+=pop[i].fit;
			if(pop[i].fit>pop[maxPopIndex].fit){
				maxPopIndex=i;
			}
		}
		//�õ���ǰ����ƽ����Ӧ��
		averageFit=sumFit/popSize;
		//���浱ǰ����Ӧ�����ֵ�����Ӧ�Ļ���
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
	 * ѡ������
	 */
	private void select(){
		//��Ӧ����ת��
		double[] tempFit=new double[popSize];
		for(int i=0;i<popSize;i++){
			if(currentGeneration<=0.4*mostGeneration){
				tempFit[i]=Math.abs(pop[i].fit-averageFit);
			}else{
				tempFit[i]=Math.tan(currentGeneration/mostGeneration)*(Math.PI/4)*pop[i].fit+pop[popSize].fit;
			}
		}
		//���任�����Ӧ����ȡÿ����Ⱥ����Ӧ��ѡ�����
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
		//���̶�ѡ��ʼ
		while(newPopNum<popSize){
			float pick=random.nextFloat();
			double currentSum=0;
			int i=0;
			while(currentSum<pick){
				currentSum=currentSum+selectPosibility[i];
				i++;
			}
			//����ѡ������ĸ�����뽻���
			for(int p=0;p<pop[i-1].popGeneData.length;p++){
				for(int q=0;q<pop[i-1].popGeneData[p].length;q++){
					crossMutationPool[newPopNum].popGeneData[p][q]=pop[i-1].popGeneData[p][q];
				}
			}
			crossMutationPool[newPopNum].fit=pop[i-1].fit;
			//ѡ����һ����
			newPopNum++;
		}
	}
	/**
	 * ��������
	 */
	private void cross(){
		int[] w=new int[pbParam.pbWorkPersonNum];
		for(int i=0;i<pbParam.pbWorkPersonNum;i++){
			//���ھ��Ƚ���,�ж��Ƿ񽻻���Ӧλ���ϵ�����
			w[i]=randbit(0,2,1);
		}
		//���ڴ��ÿ���������������ĸ�����(����2+�Ӵ�2)
		PopStruct[] tempFour=new PopStruct[4];
		//�����������
		startPopGeneData(tempFour);
		//���ڴ��ÿ�η�������������2���Ӵ�����
		PopStruct[] tempTwo=new PopStruct[2];
		//�����������
		startPopGeneData(tempTwo);
		for(int k=0;k<popSize;){
			Random random=new Random();
			int numIndex=0;
			boolean change=false;
			//���㵱ǰ����������ĸ���Ľ�����
			double crossP=0.0;
			double moreFit=Math.max(crossMutationPool[k].fit, crossMutationPool[k+1].fit);
			if(moreFit>=averageFit){
				crossP=0.9-0.5*(moreFit-averageFit)/(pop[maxPopIndex].fit-averageFit);
			}else{
				crossP=0.9;
			}
			//ת������������Ⱦɫ��
			for(int i=0;i<crossMutationPool[k].popGeneData.length;i++){
				for(int j=0;j<crossMutationPool[k].popGeneData[i].length;j++){
					tempTwo[0].popGeneData[i][j]=crossMutationPool[k].popGeneData[i][j];
					tempTwo[1].popGeneData[i][j]=crossMutationPool[k+1].popGeneData[i][j];
				}
			}
			//��ʼ����
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
			//����������,��ͨ������ѡ������������������Ӵ���ѡȡ�������ŵĸ�����Ϊ��һ����Ⱥ����
			if(change){
				tempTwo[0].fit=calCulateFitness(tempTwo[0].popGeneData);
				tempTwo[1].fit=calCulateFitness(tempTwo[1].popGeneData);
				//�����ĸ�����,ȡ�����������ŵ�����������Ϊ��һ����Ⱥ����
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
				//���ĸ����������Ӧ���ɴ�С����
				paiXu(tempFour, tempFour.length);
				//ȡ�������ŵ���Ϊ��һ���ĸ���
				for(int p=0;p<crossMutationPool[k].popGeneData.length;p++){
					for(int q=0;q<crossMutationPool[k].popGeneData[p].length;q++){
						crossMutationPool[k].popGeneData[p][q]=tempFour[0].popGeneData[p][q];
						crossMutationPool[k+1].popGeneData[p][q]=tempFour[1].popGeneData[p][q];
					}
				}
				crossMutationPool[k].fit=tempFour[0].fit;
				crossMutationPool[k+1].fit=tempFour[1].fit;
			}
			//ѡ����һ�Խ���ĸ���
			k+=2;
		}
	}
	/**
	 * ��������
	 */
	private void mutation(){
		int[] w=new int[pbParam.pbWorkPersonNum];
		for(int i=0;i<pbParam.pbWorkPersonNum;i++){
			//���ڱ���,ѡ��������ֵ�ķ�Χ
			w[i]=randbit(0,2,i);
		}
		for(int i=0;i<popSize;i++){
			Random random=new Random();
			//��Ǹ����Ƿ�������
			boolean change=false;
			//���㵱ǰ����ı�����
			double mutationP=0;
			if(crossMutationPool[i].fit>=averageFit){
				mutationP=0.1-0.05*(crossMutationPool[i].fit-averageFit)/(pop[maxPopIndex].fit-averageFit);
			}else{
				mutationP=0.1;
			}
			//���ȱ���
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
				//����������,��������������Ӧ��,��δ,��ֱ�Ӷ���һ��������б���
				if(change){
					crossMutationPool[i].fit=calCulateFitness(crossMutationPool[i].popGeneData);
				}
			}
		}
	}
	/**
	 * ��Ⱥ���¼�������һ����Ⱥ
	 */
	private void update(){
		//����һ����Ⱥ����Ӧ���ɴ�С����
		paiXu(pop,popSize);
		//�Խ�������ĸ��尴��Ӧ���ɴ�С����
		paiXu(crossMutationPool,popSize);
		int j=crossMutationPool.length-1;
		//����һ���д��ڽ���������Ⱥ�������Ӧ�ȵ����и���ȡ��������������ݵ�������
		//���ϴ������Ÿ�����ڴ˴������Ÿ���ʱ,��Ӣ����������ٸ�����Ⱥ,������Ҫȡ��ֱ�Ӹ�����Ⱥ
		if(pop[0].fit>crossMutationPool[0].fit){
			//��Ӣ����
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
			//������Ⱥ
			for(int i=0;i<pop.length-1;i++){
				for(int p=0;p<pop[i].popGeneData[p].length;p++){
					for(int q=0;q<pop[i].popGeneData[p].length;q++){
						pop[i].popGeneData[p][q]=crossMutationPool[i].popGeneData[p][q];
					}
				}
				pop[i].fit=crossMutationPool[i].fit;
			}
		}else{
			//����Ҫ����Ӣ����,��ֱ�Ӹ��²�����һ����Ⱥ
			//������Ⱥ
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
	 * �õ������������,����ÿ��ʱ������õ��ϰ�����
	 */
	private void getCurrentDayBestFitnessAndPopChoice(){
		//��Ÿ����������
		pbParam.pbEachDayBestChoice=new int[pbParam.banCi.length];
		//�����Ż������õ�ÿ����ΰ��ŵ��ϰ�����
		for(int i=0;i<pop[popSize].popGeneData.length;i++){
			for(int j=0;j<pop[popSize].popGeneData[i].length;j++){
				pbParam.pbEachDayBestChoice[pop[popSize].popGeneData[i][j]]+=1;
			}
		}
		//�������ÿ��ʱ����ʵ�ʰ�������
		pbParam.pbEachDayTimePointSetPerson=new int[pbParam.pbTimePointNum];
		for(int j=0;j<pbParam.pbTimePointNum;j++){
			for(int k=0;k<pbParam.banCi.length;k++){
				if(pbParam.pbEachDayBestChoice[k]>0&&pbParam.banCi[k].pbBanCiPoint[j]>0){
					pbParam.pbEachDayTimePointSetPerson[j]=pbParam.pbEachDayTimePointSetPerson[j]+pbParam.pbEachDayBestChoice[k]*pbParam.banCi[k].pbBanCiPoint[j];
				}				
			}
		}
		//���������ʹ���˿�ҹ�����һ��ÿ��ʱ���Ĳ�������������
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
	 * ���ת������23:55ת���ɾ�������ְ��:15����Ϊ96��30����Ϊ:48
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
	 * ����:
	 */
	private void encode(){
		this.pop=new PopStruct[this.popSize+1];
		//�������Ļ������鼰��ʼ��������Ӧ��
		startPopGeneData(this.pop);
		for(int i=0;i<popSize;i++){
			for(int j=0;j<pop[i].popGeneData.length;j++){
				//��������(�����������),ע�����Ű������ʼ��ʱ�Ѿ�����ι��࣬����������ǰ���������ں�
				for(int k=0;k<pop[i].popGeneData[j].length;k++){
					if(j==0){
						//���������ÿ���˽��а�κű���
						pop[i].popGeneData[j][k]=randbit(0,pbParam.pbTiaoBanCiNum,k);
					}else{
						//���Ϸ������ÿ���˽��а�κű���
						pop[i].popGeneData[j][k]=randbit(pbParam.pbTiaoBanCiNum,pbParam.banCi.length,k);
					}
				}	
			}
			//����ø������Ӧ��
			pop[i].fit=calCulateFitness(pop[i].popGeneData);
		}
	}
	/**
	 * �������������鲢��ʼ��������Ӧ��
	 * @param pop
	 */
	private void startPopGeneData(PopStruct[] pop){
		for(int i=0;i<pop.length;i++){
			//���ÿ����Ⱥ����Ļ���
			pop[i].popGeneData=new int[2][];
			//����ﵽ���Լ�����ұ�֤�ϰ������ﵽҪ��
			pop[i].popGeneData[0]=new int[pbParam.pbTiaoBanPersonMax];
			
			pop[i].popGeneData[1]=new int[pbParam.pbWorkPersonNum-pbParam.pbTiaoBanPersonMax];
			pop[i].fit=0;
		}
	}
	/**
	 * ������Ⱥ������Ӧ��
	 * @param popGeneData
	 * @return
	 */
	public double calCulateFitness(int[][] popGeneData){
		double similar=0.0;
		//��Ⱦɫ����룬��ͳ��ÿ����ΰ��Ź�������
		int[] popDecodeChoice=new int[pbParam.banCi.length];
		for(int j=0;j<popGeneData.length;j++){
			for(int k=0;k<popGeneData[j].length;k++){
				//ͳ��ÿ������ϰ��������
				popDecodeChoice[popGeneData[j][k]]+=1;
			}
		}
		//����϶�
		int deltaSetNeedofDay=0;
		for(int j=0;j<pbParam.pbTimePointNum;j++){
			int setPersonofTimePoint=0;
			for(int k=0;k<pbParam.banCi.length;k++){
				//��ɽ̫��ҹ�����ʱ���޸�setPersonofTimePoint���㷽ʽ
				//�ж��Ƿ��ҹ
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
					//������������0����������Ϊ0ʱ,����ͷ�����
					deltaSetNeedofDay=deltaSetNeedofDay+2000*pbParam.pbOneDayNeedPersonSqrSum*pbParam.pbTimePointNeetPerson[j]/pbParam.pbTimePointNum;
				}else if(pbParam.pbTimePointNeetPerson[j]==0&&setPersonofTimePoint!=0){
					//����������Ϊ0������������0ʱ,����ͷ�����
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
					//������������0����������Ϊ0ʱ,����ͷ�����
					deltaSetNeedofDay=deltaSetNeedofDay+200*pbParam.pbOneDayNeedPersonSqrSum*pbParam.pbTimePointNeetPerson[j]/pbParam.pbTimePointNum;
				}else if(pbParam.pbTimePointNeetPerson[j]==0&&setPersonofTimePoint!=0){
					//����������Ϊ0������������0ʱ,����ͷ�����
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
		//����Щ��Ŀ�У�����Ա������ԣʱ������ÿ��������Ӧ�Ⱥ�����Ϊ��ֵ���ʽ���Ӧ�Ⱥ�������ת��
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
	 * �������
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
	 * ����ķ���
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
	/*�˷��������Բ������ĵı�д��,
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
