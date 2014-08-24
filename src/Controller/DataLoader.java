/**
 * 
 */
package Controller;

import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import net.proteanit.sql.DbUtils;
import PModel.Activity;
import PModel.Member;
import PModel.MemberActivity;
import PModel.Project;

/**
 *
 */
public class DataLoader {

	public void loadData(MainController mc) {

		String sqlMembers = "select * from Members";
		String sqlProjects = "select * from Projects";
		String sqlActivities = "select * from Activities";
		String sqlMemberActivities = "select * from MemberActivities";
		try {
			mc.pst = mc.conn.prepareStatement(sqlMembers);
			mc.rs = mc.pst.executeQuery();
			while (mc.rs.next()) {
				Member member = new Member(mc.rs.getString("Name"),
						mc.rs.getString("Type"), mc.rs.getString("username"),
						mc.rs.getString("password"));
				member.setMemberID(mc.rs.getInt("MID"));
				mc.members.add(member);
			}
			mc.rs.close();
			mc.pst.close();
			mc.pst = mc.conn.prepareStatement(sqlProjects);
			mc.rs = mc.pst.executeQuery();
			while (mc.rs.next()) {
				Project project = new Project(mc.rs.getInt("ManagerID"),
						mc.rs.getString("Name"),
						mc.rs.getString("Description"),
						mc.rs.getDate("StartDate"),
						mc.rs.getDouble("PercentComplete"),
						mc.rs.getDouble("BudgetAtCompletion"),
						mc.rs.getDouble("PercentScheduledForCompletion"),
						mc.rs.getDouble("ActualCost"),
						mc.rs.getDouble("EarnedValue"),
						mc.rs.getDouble("CostVariance"),
						mc.rs.getDouble("ScheduleVariance"),
						mc.rs.getDouble("CostPerformanceIndex"),
						mc.rs.getDouble("SchedulePerformanceIndex"),
						mc.rs.getDouble("EstimateAtCompletion"),
						mc.rs.getDouble("EstimateToComplete"));
				project.setProjectID(mc.rs.getInt("PID"));
				mc.projects.add(project);
			}
			mc.rs.close();
			mc.pst.close();
			mc.pst = mc.conn.prepareStatement(sqlActivities);
			mc.rs = mc.pst.executeQuery();

			while (mc.rs.next()) {
				Activity activity = new Activity(mc.rs.getInt("PID"),
						mc.rs.getString("Name"),
						mc.rs.getString("Description"),
						mc.rs.getDouble("PlannedValue"),
						mc.rs.getDouble("MostLikelyTimeToCompletion"),
						mc.rs.getDouble("OptimisticTimeToCompletion"),
						mc.rs.getDouble("PessimisticTimeToCompletion"),
						mc.rs.getDouble("TargetCompletionDate"),
						mc.rs.getDouble("PercentComplete"),
						mc.rs.getDouble("ActualCost"),
						mc.rs.getBoolean("Status"));
				activity.setProjectID(mc.rs.getInt("PID"));
				activity.setNumber(mc.rs.getInt("Number"));
				mc.activities.add(activity);
			}
			mc.rs.close();
			mc.pst.close();
			mc.pst = mc.conn.prepareStatement(sqlMemberActivities);
			mc.rs = mc.pst.executeQuery();
			while (mc.rs.next()) {
				MemberActivity ma = new MemberActivity(mc.rs.getInt("MID"),
						mc.rs.getInt("PID"), mc.rs.getInt("Number"));
				mc.memberActivities.add(ma);
			}
			mc.rs.close();
			mc.pst.close();
		} catch (Exception ex) {
		}

	}

	public ArrayList<Integer> getRelatedActivities(MainController mc,
			Activity a, String type) {

		String sql, get;
		ArrayList<Integer> temp = new ArrayList<Integer>();

		if (type.equals("dependent")) {
			sql = "select Number "
					+ "from ActivityDependency "
					+ "where DependantOnPID = ? and DependantOnNumber = ? and PID = ?";
			get = "Number";
		} else {
			sql = "select DependantOnNumber " + "from ActivityDependency "
					+ "where PID = ? and Number = ? and DependantOnPID = ?";
			get = "DependantOnNumber";
		}

		try {
			mc.pst = mc.conn.prepareStatement(sql);
			mc.pst.setInt(1, a.getProjectID());
			mc.pst.setInt(2, a.getNumber());
			mc.pst.setInt(3, a.getProjectID());
			mc.rs = mc.pst.executeQuery();

			while (mc.rs.next()) {
				temp.add(mc.rs.getInt(get));
			}
		} catch (Exception ex) {
		} finally {
			try {
				mc.rs.close();
				mc.pst.close();
			} catch (Exception e) {
			}
		}
		return temp;
	}

	public void getTableFormattedActivityList(MainController mc) {
		int pid = mc.getCurrentProject().getProjectID();
		String sql = "select * from Activities  where PID = ?";
		try {
			mc.pst = mc.conn.prepareStatement(sql);
			mc.pst.setInt(1, pid);
			mc.rs = mc.pst.executeQuery();
			DisplayController.get().getActivityTable()
					.setModel(DbUtils.resultSetToTableModel(mc.rs));
			mc.pst.execute();
			mc.pst.close();
		} catch (Exception ex) {

		}
	}

	public void getActivityListForCurrentTeamMember(MainController mc) {
		int mid = mc.currentUser.getMemberID();

		String sql = "select Activities.PID, Activities.Number, Activities.Name"
				+ " from Activities, MemberActivities"
				+ "  where MemberActivities.PID = Activities.PID   AND "
				+ " 	 	 MemberActivities.Number = Activities.Number   AND"
				+ "  		 MemberActivities.MID = ?";
		try {
			mc.pst = mc.conn.prepareStatement(sql);
			mc.pst.setInt(1, mid);
			mc.rs = mc.pst.executeQuery();
			DisplayController.get().getActivityTable()
					.setModel(DbUtils.resultSetToTableModel(mc.rs));
			mc.pst.execute();
			mc.pst.close();
		} catch (Exception ex) {

		}
	}

	public void getEmptyActivityTable(MainController mc) {
		String sql = "select * from Activities where PID = -1";
		try {
			mc.pst = mc.conn.prepareStatement(sql);
			mc.rs = mc.pst.executeQuery();
			DisplayController.get().getActivityTable()
					.setModel(DbUtils.resultSetToTableModel(mc.rs));
			mc.pst.execute();
			mc.pst.close();
		} catch (Exception ex) {
		}
	}
}