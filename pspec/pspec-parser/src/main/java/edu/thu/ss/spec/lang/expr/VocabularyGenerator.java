package edu.thu.ss.spec.lang.expr;

import java.io.File;

import edu.thu.ss.spec.lang.parser.VocabularyWriter;
import edu.thu.ss.spec.lang.parser.WritingException;
import edu.thu.ss.spec.lang.pojo.ContactInfo;
import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.lang.pojo.DataContainer;
import edu.thu.ss.spec.lang.pojo.DesensitizeOperation;
import edu.thu.ss.spec.lang.pojo.Info;
import edu.thu.ss.spec.lang.pojo.UserCategory;
import edu.thu.ss.spec.lang.pojo.UserContainer;
import edu.thu.ss.spec.lang.pojo.Vocabulary;

public class VocabularyGenerator {

	private static int[] users = { 10, 20, 30, 40, 50 };

	private static int[] datas = { 20, 40, 60, 80, 100 };

	private static int[] ops = { 5, 10, 15, 20, 25 };

	private static int defaultUsers = 30;

	private static int defaultDatas = 60;

	private static int defaultOps = 15;

	public static void main(String[] args) throws WritingException {
		String dir = "misc/experiment/";
		for (int user : users) {
			generate(dir, user, defaultDatas, defaultOps);
		}
		for (int data : datas) {
			generate(dir, defaultUsers, data, defaultOps);
		}
		for (int op : ops) {
			generate(dir, defaultUsers, defaultDatas, op);
		}
	}

	public static void generate(String dir, int users, int datas, int ops) throws WritingException {
		Info vocabInfo = new Info();

		ContactInfo contact = new ContactInfo();
		contact.setAddress("Beijing China");
		contact.setCountry("China");
		contact.setEmail("luochen01@vip.qq.com");
		contact.setOrganization("Tsinghua University");
		contact.setName("Luo Chen");
		vocabInfo.setContact(contact);

		vocabInfo.setId("exp-vocab");

		Vocabulary vocab = new Vocabulary();
		vocab.setInfo(vocabInfo);

		vocab.setUserContainer(generateUsers("", users));
		vocab.setDataContainer(generateDatas("", datas, defaultOps));

		VocabularyWriter writer = new VocabularyWriter();
		File dirFile = new File(dir);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}

		File file = new File(dirFile, "expr-" + users + "-" + datas + "-" + ops + ".xml");
		writer.output(vocab, file.getAbsolutePath());

	}

	public static UserContainer generateUsers(String id, int num) {
		UserContainer container = new UserContainer();
		container.setId(id);

		int[] levels = new int[3];
		levels[0] = 1;
		levels[1] = Math.min(num - 1, 5);
		if (levels[1] > 0) {
			levels[2] = num - 6;
		}
		int counter = 0;
		for (int n = 0; n < levels[0]; n++) {
			UserCategory user = new UserCategory();
			user.setId("analyst" + counter);
			counter++;
			container.add(user);
		}
		for (int n = 0; n < levels[1]; n++) {
			UserCategory user = new UserCategory();
			user.setId("analyst" + counter);
			user.setParentId("analyst" + 0);
			counter++;
			container.add(user);
		}
		for (int n = 0; n < levels[2]; n++) {
			UserCategory user = new UserCategory();
			user.setId("analyst" + counter);
			user.setParentId("analyst" + (counter % 5 + 1));
			counter++;
			container.add(user);
		}
		return container;

	}

	public static DataContainer generateDatas(String id, int num, int opNum) {
		DataContainer container = new DataContainer();
		container.setId(id);
		int[] levels = new int[3];
		levels[0] = 1;
		levels[1] = Math.min(num - 1, 5);
		if (levels[1] > 0) {
			levels[2] = num - 6;
		}
		int counter = 0;

		{
			DataCategory data = new DataCategory();
			data.setId("data" + counter);
			counter++;
			container.add(data);
			for (int i = 1; i <= opNum; i++) {
				data.getOperations().add(DesensitizeOperation.get("op" + i));
			}
		}

		for (int n = 0; n < levels[1]; n++) {
			DataCategory data = new DataCategory();
			data.setId("data" + counter);
			data.setParentId("data" + 0);
			counter++;
			container.add(data);
		}
		for (int n = 0; n < levels[2]; n++) {
			DataCategory data = new DataCategory();
			data.setId("data" + counter);
			data.setParentId("data" + (counter % 5 + 1));
			counter++;
			container.add(data);
		}
		return container;

	}
}
