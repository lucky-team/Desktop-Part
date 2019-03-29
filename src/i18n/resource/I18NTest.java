package i18n.resource;

import java.util.Locale;
import java.util.ResourceBundle;

public class I18NTest 
{

	public static void main(String[] args) 
	{
		//��Դ������(����+myproperties)
        String basename = "i18n.resource.myproperties";
        //�������Ի���
        Locale cn = Locale.CHINA;//����
        Locale uk = Locale.UK;//Ӣ��
        //���ݻ��������Ի������ض�Ӧ��������Դ�ļ�
        ResourceBundle myResourcesCN = ResourceBundle.getBundle(basename,cn);//����myproperties_zh.properties
        ResourceBundle myResourcesUK = ResourceBundle.getBundle(basename,uk);//����myproperties_en.properties
         
        //������Դ�ļ��� ����Ϳ��Ե���ResourceBundleʵ������� getString������ȡָ������Դ��Ϣ��������Ӧ��ֵ��
        //String value =  myResources.getString(��key");
        String usernameCN = myResourcesCN.getString("username");
        String passwordCN = myResourcesCN.getString("password");
         
        String usernameUS = myResourcesUK.getString("username");
        String passwordUS = myResourcesUK.getString("password");
         
        System.out.println(usernameCN+"--"+passwordCN);
        System.out.println(usernameUS+"--"+passwordUS);
	}

}