/**
 * 
 */
package Controller;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import PModel.Activity;
import PModel.Member;
import PModel.Project;

/**
 *
 */
public class DataUpdater {

	/**
	 * @param mc
	 * @param member
	 * @return
	 */
	public Member createMember(MainController mc, Member member) {
		if (validMember(mc, member)) {

			for (Member theMember : mc.members)
				if (theMember.getUserName().equals(member.getUserName()))
					ErrorController.get().AddError(
							"Member with Username " + theMember.getUserName()
									+ " already exists.");
			if (ErrorController.get().ErrorsExist()) {
				ErrorController.get().DisplayErrors();
				return null;
			}
			String sql = "insert into Members (Name,Type,Username,Password)values(?,?,?,?)";
			try {
				mc.pst = mc.conn.prepareStatement(sql);
				mc.pst.setString(1, member.getName());
				mc.pst.setString(2, member.getType());
				mc.pst.setString(3, member.getUserName());
				mc.pst.setString(4, member.getPassword());
				mc.pst.execute();
				mc.pst.close();
				sql = "select max(MID) from Members";
				mc.pst = mc.conn.prepareStatement(sql);
				mc.rs = mc.pst.executeQuery();
				member.setMemberID(mc.rs.getInt(1));
				mc.members.add(member);
				return member;
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex);
			}
		}
		return null;
	}

	/**
	 * @param mc
	 * @param member
	 * @return
	 */
	public boolean updateMember(MainController mc, Member member) {
		if (validMember(mc, member)) {
			String sql;
			try {
				sql = "select count(*) from Members where MID = ?";
				mc.pst = mc.conn.prepareStatement(sql);
				mc.pst.setInt(1, member.getMemberID());
				mc.rs = mc.pst.executeQuery();
				if (mc.rs.getInt(1) == 0) {
					ErrorController.get().AddError(
							"Member with ID " + member.getMemberID()
									+ " does not exist.");
				}
			} catch (SQLException ex) {
			}
			if (ErrorController.get().ErrorsExist()) {
				ErrorController.get().DisplayErrors();
				return false;
			}
			sql = "update Members set name=?,type=?,username=?,password=? where MID = ?";
			try {
				mc.pst = mc.conn.prepareStatement(sql);
				mc.pst.setString(1, member.getName());
				mc.pst.setString(2, member.getType());
				mc.pst.setString(3, member.getUserName());
				mc.pst.setString(4, member.getPassword());
				mc.pst.setInt(5, member.getMemberID());
				mc.pst.execute();
				mc.pst.close();
				return true;
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex);
			}
		}
		return false;
	}

	/**
	 * @param mc
	 * @param member
	 * @return
	 */
	private boolean validMember(MainController mc, Member member) {
		if (member.getName().equals("") || member.getName() == null
				|| member.getType().equals("") || member.getType() == null
				|| member.getUserName().equals("")
				|| member.getUserName() == null
				|| member.getPassword().equals("")
				|| member.getPassword() == null) {
			return false;
		}

		int count = 0;
		for (Member theMember : mc.members)
			if (theMember.getUserName().equals(member.getUserName())) {
				count++;
			}
		if (count > 1) {
			ErrorController.get().AddError(
					"Member with Username " + member.getUserName()
							+ " already exists.");
			return false;
		}
		return true;
	}

	/**
	 * @param mc
	 * @param project
	 * @return
	 */
	public boolean updateProject(MainController mc, Project project) {
		if (validProject(mc, project)) {
			String sql;
			try {
				sql = "select count(*) from Projects where PID = ?";
				mc.pst = mc.conn.prepareStatement(sql);
				mc.pst.setInt(1, project.getProjectID());
				mc.rs = mc.pst.executeQuery();
				if (mc.rs.getInt(1) == 0) {
					ErrorController.get().AddError(
							"Project with ID " + project.getProjectID()
									+ " does not exist.");
				}
				sql = "select count(*) from Members where MID = ?";
				mc.pst = mc.conn.prepareStatement(sql);
				mc.pst.setInt(1, project.getManagerID());
				mc.rs = mc.pst.executeQuery();
				if (mc.rs.getInt(1) == 0) {
					ErrorController.get().AddError(
							"Manager with ID " + project.getManagerID()
									+ " does not exist.");
				}
				sql = "select count(*) from Projects where Name = ? and PID <> ?";
				mc.pst = mc.conn.prepareStatement(sql);
				mc.pst.setString(1, project.getName());
				mc.pst.setInt(2, project.getProjectID());
				mc.rs = mc.pst.executeQuery();
				if (mc.rs.getInt(1) != 0) {
					ErrorController.get().AddError(
							"Another project with the Name "
									+ project.getName() + " already exists.");
				}

			} catch (SQLException ex) {
			}
			if (ErrorController.get().ErrorsExist()) {
				ErrorController.get().DisplayErrors();
				return false;
			}
			sql = "update Projects set Name=?,Description=?,StartDate=?,PercentComplete=?,BudgetAtCompletion=?,"
					+ "PercentScheduledForCompletion=?,ActualCost=?,EarnedValue=?,"
					+ "CostVariance=?,ScheduleVariance=?,CostPerformanceIndex=?,SchedulePerformanceIndex=?,"
					+ "EstimateAtCompletion=?,EstimateToComplete=?,ManagerID=? where PID = ?";
			try {
				mc.pst = mc.conn.prepareStatement(sql);
				mc.pst.setString(1, project.getName());
				mc.pst.setString(2, project.getDescr());
				mc.pst.setDate(3, project.getStartDate());
				mc.pst.setDouble(4, project.getPercentComplete());
				mc.pst.setDouble(5, project.getBudgetAtCompletion());
				mc.pst.setDouble(6, project.getPercentScheduledForCompletion());
				mc.pst.setDouble(7, project.getActualCost());
				mc.pst.setDouble(8, project.getEarnedValue());
				mc.pst.setDouble(9, project.getCostVariance());
				mc.pst.setDouble(10, project.getScheduleVariance());
				mc.pst.setDouble(11, project.getCostPerformanceIndex());
				mc.pst.setDouble(12, project.getSchedulePerformanceIndex());
				mc.pst.setDouble(13, project.getEstimateAtCompletion());
				mc.pst.setDouble(14, project.getEstimateToComplete());
				mc.pst.setInt(15, project.getManagerID());
				mc.pst.setInt(16, project.getProjectID());

				mc.pst.execute();
				mc.pst.close();

				return true;
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex);
			}
		}
		return false;
	}

	/**
	 * @param mc
	 * @param project
	 * @return
	 */
	private boolean validProject(MainController mc, Project project) {
		return !project.getName().equals("") && project.getName() != null
				&& !project.getDescr().equals("") && project.getDescr() != null;

	}

	/**
	 * @param mc
	 * @param project
	 * @return
	 */
	public Project initializeProject(MainController mc,
			Project project) {
		if (validProject(mc, project)) {
			String sql;
			try {
				// TODO: Change to use local memory rather than DB

				sql = "select * from Projects where Name = ?";
				mc.pst = mc.conn.prepareStatement(sql);
				mc.pst.setString(1, project.getName());
				mc.rs = mc.pst.executeQuery();
				while (mc.rs.next()) {
					ErrorController.get().AddError(
							"Project with Name \"" + project.getName()
									+ "\" already exists.");
				}
				sql = "select count(*) from Members where MID = ?";
				mc.pst = mc.conn.prepareStatement(sql);
				mc.pst.setInt(1, project.getManagerID());
				mc.rs = mc.pst.executeQuery();
				if (mc.rs.getInt(1) == 0) {
					ErrorController.get().AddError(
							"Manager with ID " + project.getManagerID()
									+ " does not exist.");
				}
			} catch (SQLException ex) {
			}
			if (ErrorController.get().ErrorsExist()) {
				ErrorController.get().DisplayErrors();
				return null;
			}
			sql = "insert into Projects (Name,Description,StartDate,PercentComplete,BudgetAtCompletion,"
					+ "PercentScheduledForCompletion,ActualCost,EarnedValue,CostVariance,"
					+ "ScheduleVariance,CostPerformanceIndex,SchedulePerformanceIndex,"
					+ "EstimateAtCompletion,EstimateToComplete,ManagerID"
					+ ")values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			try {
				mc.pst = mc.conn.prepareStatement(sql);
				mc.pst.setString(1, project.getName());
				mc.pst.setString(2, project.getDescr());
				mc.pst.setDate(3, project.getStartDate());
				mc.pst.setDouble(4, project.getPercentComplete());
				mc.pst.setDouble(5, project.getBudgetAtCompletion());
				mc.pst.setDouble(6, project.getPercentScheduledForCompletion());
				mc.pst.setDouble(7, project.getActualCost());
				mc.pst.setDouble(8, project.getEarnedValue());
				mc.pst.setDouble(9, project.getCostVariance());
				mc.pst.setDouble(10, project.getScheduleVariance());
				mc.pst.setDouble(11, project.getCostPerformanceIndex());
				mc.pst.setDouble(12, project.getSchedulePerformanceIndex());
				mc.pst.setDouble(13, project.getEstimateAtCompletion());
				mc.pst.setDouble(14, project.getEstimateToComplete());
				mc.pst.setInt(15, project.getManagerID());

				mc.pst.execute();
				mc.pst.close();
				sql = "select max(PID) from Projects";
				mc.pst = mc.conn.prepareStatement(sql);
				mc.rs = mc.pst.executeQuery();
				project.setProjectID(mc.rs.getInt(1));
				mc.projects.add(project);
				return project;
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex);
			}
		}
		return null;
	}

	/**
	 * @param mc
	 * @param activity
	 */
	public Activity createActivity(MainController mc, Activity activity) {
		if (!activity.getName().equals("") && activity.getName() != null
				&& !activity.getDescr().equals("")
				&& activity.getDescr() != null) {
			String sql;
			try {
				sql = "select count(*) from Activities where PID = ? and Number = ?";
				mc.pst = mc.conn.prepareStatement(sql);
				mc.pst.setInt(1, activity.getProjectID());
				mc.pst.setInt(2, activity.getNumber());
				mc.rs = mc.pst.executeQuery();
				if (mc.rs.getInt(1) == 1) {
					ErrorController.get().AddError(
							"Activity with PID " + activity.getProjectID()
									+ " and Number " + activity.getNumber()
									+ " already exists.");
				}
			} catch (SQLException ex) {
			}
			if (ErrorController.get().ErrorsExist()) {
				ErrorController.get().DisplayErrors();
				return null;
			}
			try {
				// Here we get the max number for the current project, as with 2
				// primary keys we cannot auto-increment them.
				sql = "select Max(number) from Activities where PID = ?";
				mc.pst = mc.conn.prepareStatement(sql);
				mc.pst.setInt(1, mc.currentProject.getProjectID());
				mc.rs = mc.pst.executeQuery();
				int x = mc.rs.getInt(1);
				x++;
				sql = "insert into Activities "
						+ "(PID,Number,Name,Description,PlannedValue,MostLikelyTimeToCompletion,"
						+ "OptimisticTimeToCompletion,PessimisticTimeToCompletion,TargetCompletionDate,Status,ActualCost,PercentComplete)"
						+ "values(?,?,?,?,?,?,?,?,?,?,?,?)";
				mc.pst = mc.conn.prepareStatement(sql);
				mc.pst.setInt(1, mc.currentProject.getProjectID());
				mc.pst.setInt(2, x);
				mc.pst.setString(3, activity.getName());
				mc.pst.setString(4, activity.getDescr());
				mc.pst.setDouble(5, activity.getPlannedValue());
				mc.pst.setDouble(6, activity.getMostLikelyTimeToCompletion());
				mc.pst.setDouble(7, activity.getOptimisticTimeToCompletion());
				mc.pst.setDouble(8, activity.getPessimisticTimeToCompletion());
				mc.pst.setDouble(9, activity.getTargetCompletionDate());
				mc.pst.setBoolean(10, activity.getStatus());
				mc.pst.setDouble(11, activity.getActualCost());
				mc.pst.setDouble(12, activity.getPercentComplete());
				mc.pst.execute();
				mc.pst.close();
				activity.setProjectID(mc.currentProject.getProjectID());
				activity.setNumber(x);
				mc.activities.add(activity);
				return activity;
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex);
			}
		}
		return null;
		
	}

}
