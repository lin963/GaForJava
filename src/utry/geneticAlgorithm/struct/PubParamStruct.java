package utry.geneticAlgorithm.struct;
/**
 * ���ò����ṹ��
 * @author DFF
 *
 */

import java.util.Date;

import utry.util.datatable.DataTable;

public class PubParamStruct {

	//רҵID
	public String zhuanyeId;
	//�������,����Ψһʶ���Ű���Ŀ,�����Ű�ģ������˵����Ŀ
	public String schAreaCode;
	//�Ű�������ʼ��
	public Date dateBegin;
	//�Ű����ڽ�����
	public Date dateEnd;
	//�Űྫ��
	public int precison;
	//����������
	public DataTable manPowerNeed;
	//ϵͳ�Ű����
	public String csBeginPointString;
	//ϵͳ�Ű�ֹ��
	public String csEndPointString;
	//�Ű������ڵ�����
	public int dmonth;
	//�Ű�ʱ������
	public int btime;
	//ÿ������ÿ��Ⱥ����Ű൥λ����
	public int[][] nteam;
	//ÿ������ÿ��Ⱥ���ȡ�����ڰ�������
	public int[][] dlast;
	//�Ű���ʼ�㣬ʱ������ʽ
	public int beginPointInt;
	//�Ű���ֹ�㣬ʱ������ʽ
	public int endPointInt;
	//�Ƿ������ϸ����ڵ��ν�
	public boolean whetherLinkupWithLastPeriod;
	//
	public int byType;
	//�Ƿ���Ҫ���㲹�Ӱ��
	public boolean whetherAddRest;
	//��θ�ԣ�ʣ�ֻ�а�θ�ԣ�ڵ�/��θ��ǽڵ�>addRestProp�Ž����β���
	public float addRestProp;
	//�Ƿ�ѡ����6��2ģʽ
	public boolean isOn6Off2;	
	
}
