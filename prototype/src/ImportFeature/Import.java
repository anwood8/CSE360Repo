package ImportFeature;
import application.User;
import java.io.*;

public class Import {
	public void importfromcsv(User user) throws IOException {
		String file = "UserData/save.csv";
		BufferedReader reader = null;
		String line = "";
		try {
			reader = new BufferedReader(new FileReader(file));
			int projectnum = 0;
			boolean firstrow = true;
			while((line = reader.readLine()) != null) {
				if(firstrow) {
					firstrow = false;
					continue;
				}
				String[] row = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
				user.addProject(row[0].replace("\"",""));
				String lifecycle = row[1].replace("\"","");
				lifecycle = lifecycle.replace("[", "");
				lifecycle = lifecycle.replace("]", "");
				lifecycle = lifecycle.replace(" ", "");
				String[] lifecyclerow = lifecycle.split(",");
				for (String value : lifecyclerow) {
					user.addLifecycleStep(value, projectnum);
				}
				String plans = row[2].replace("\"","");
				plans = plans.replace("[", "");
				plans = plans.replace("]", "");
				plans = plans.replace(" ", "");
				String[] plansrow = plans.split(",");
				for (String value1 : plansrow) {
					user.addDeliverable(value1, projectnum, 0);
				}
				String deliver = row[3].replace("\"","");
				deliver = deliver.replace("[", "");
				deliver = deliver.replace("]", "");
				deliver = deliver.replace(" ", "");
				String[] deliverrow = deliver.split(",");
				for (String value2 : deliverrow) {
					user.addDeliverable(value2, projectnum, 1);
				}
				String interrupt = row[4].replace("\"","");
				interrupt = interrupt.replace("[", "");
				interrupt = interrupt.replace("]", "");
				interrupt = interrupt.replace(" ", "");
				String[] interruptrow = interrupt.split(",");
				for (String value3 : interruptrow) {
					user.addDeliverable(value3, projectnum, 2);
				}
				String defect = row[5].replace("\"","");
				defect = defect.replace("[", "");
				defect = defect.replace("]", "");
				defect = defect.replace(" ", "");
				String[] defectrow = defect.split(",");
				for (String value4 : defectrow) {
					user.addDeliverable(value4, projectnum, 3);
				}
				String other = row[6].replace("\"","");
				other = other.replace("[", "");
				other = other.replace("]", "");
				other = other.replace(" ", "");
				String[] otherrow = other.split(",");
				for (String value5 : otherrow) {
					user.addDeliverable(value5, projectnum, 4);
				}
				projectnum++;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			reader.close();
		}
	}
}
