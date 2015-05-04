package edu.thu.ss.spec.lang.exp;

import edu.thu.ss.spec.lang.parser.VocabularyWriter;
import edu.thu.ss.spec.lang.pojo.ContactInfo;
import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.lang.pojo.DataContainer;
import edu.thu.ss.spec.lang.pojo.DesensitizeOperation;
import edu.thu.ss.spec.lang.pojo.Info;
import edu.thu.ss.spec.lang.pojo.UserCategory;
import edu.thu.ss.spec.lang.pojo.UserContainer;
import edu.thu.ss.spec.lang.pojo.Vocabulary;
import edu.thu.ss.spec.util.WritingException;

public class VocabularyGenerator {

	public static void main(String[] args) throws WritingException {
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

		vocab.addUserContainer(generateUsers("expr-user-3", 3));
		vocab.addUserContainer(generateUsers("expr-user-5", 5));
		vocab.addUserContainer(generateUsers("expr-user-10", 10));
		vocab.addUserContainer(generateUsers("expr-user-15", 15));
		vocab.addUserContainer(generateUsers("expr-user-20", 20));

		vocab.addDataContainer(generateDatas("expr-data-20", 20, 15));
		vocab.addDataContainer(generateDatas("expr-data-40", 40, 15));
		vocab.addDataContainer(generateDatas("expr-data-60", 60, 15));
		vocab.addDataContainer(generateDatas("expr-data-80", 80, 15));
		vocab.addDataContainer(generateDatas("expr-data-100", 100, 15));

		vocab.addDataContainer(generateDatas("expr-data-op-5", 60, 5));
		vocab.addDataContainer(generateDatas("expr-data-op-10", 60, 10));
		vocab.addDataContainer(generateDatas("expr-data-op-15", 60, 15));
		vocab.addDataContainer(generateDatas("expr-data-op-20", 60, 20));
		vocab.addDataContainer(generateDatas("expr-data-op-25", 60, 25));

		VocabularyWriter writer = new VocabularyWriter();
		writer.output(vocab, "expr/expr-vocab.xml");
	}

	public static UserContainer generateUsers(String id, int num) {
		UserContainer container = new UserContainer();
		container.setId(id);

		int[] levels = new int[3];
		levels[0] = 1;
		levels[1] = Math.min(num - 1, 3);
		if (levels[1] > 0) {
			levels[2] = num - 4;
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
			user.setParentId("analyst" + (counter % 3 + 1));
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
		levels[1] = Math.min(num - 1, 3);
		if (levels[1] > 0) {
			levels[2] = num - 4;
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
			data.setParentId("data" + (counter % 3 + 1));
			counter++;
			container.add(data);
		}
		return container;

	}
}
