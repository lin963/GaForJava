package utry.prd.empty.datastruct;
/**
 * ��νṹ
 * @author DFF
 *
 */

public class PbBanCi {
	//��ε�ʱ���(��������εİ��)
	public int[] banCiDuanPoint;
	//��νڵ㻯(���Űྫ��Ϊ30ʱ��ά��Ϊ:ÿ��ʱ�����(��48,���Űྫ�Ⱦ���)����ֵΪ0��1)
	public int[] pbBanCiPoint;
	//������(������������)ֵΪ(0��1)
	public String seriesId;
	//������:�����а�����
	public String seriesLb;
	//�������(08:00-11:30	13:30-17:30)
	public String description;
	// ��ǰ���Ƿ��ҹ
	public boolean wheCrossNight;
	//(��ҹ��ڶ���)��νڵ㻯(���Űྫ��Ϊ30ʱ��ά��Ϊ��ÿ���ʱ���������48�����Űྫ�Ⱦ���������ֵΪ0��1
	public int[] pbBanCiPointNextD;
}
