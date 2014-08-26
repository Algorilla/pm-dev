/**
 * 
 */
package Controller;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import PModel.Activity;
import PModel.Member;
import PModel.MemberActivity;
import PModel.Project;

/**
 *
 */
public class DataUpdater {

	/**
	 * @param memberc
	 * @param member
	 * @return
	 */
	public Member createMember(MainController mc, Member member) {
		if (validMember(mc, member)) {

			for (Member theMember : mc.members)
				if (theMember.getUserName().equals(member.getUserName()))
					ErrorController.get().addError(
							"Member with Username " + theMember.getUserName()
									+ " already exists.");
			if (ErrorController.get().errorsExist()) {
				ErrorController.get().displayErrors();
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
				// JOptionPane.showMessageDialog(null, ex);

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
					ErrorController.get().addError(
							"Member with ID " + member.getMemberID()
									+ " does not exist.");
				}
			} catch (SQLException ex) {
			}
			if (ErrorController.get().errorsExist()) {
				ErrorController.get().displayErrors();
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
				// JOptionPane.showMessageDialog(null, ex);// DMITRI (MC
				// shouldn't be taking to the GUI)
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
			ErrorController.get().addError(
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
					ErrorController.get().addError(
							"Project with ID " + project.getProjectID()
									+ " does not exist.");
				}
				sql = "select count(*) from Members where MID = ?";
				mc.pst = mc.conn.prepareStatement(sql);
				mc.pst.setInt(1, project.getManagerID());
				mc.rs = mc.pst.executeQuery();
				if (mc.rs.getInt(1) == 0) {
					ErrorController.get().addError(
							"Manager with ID " + project.getManagerID()
									+ " does not exist.");
				}
				sql = "select count(*) from Projects where Name = ? and PID <> ?";
				mc.pst = mc.conn.prepareStatement(sql);
				mc.pst.setString(1, project.getName());
				mc.pst.setInt(2, project.getProjectID());
				mc.rs = mc.pst.executeQuery();
				if (mc.rs.getInt(1) != 0) {
					ErrorController.get().addError(
							"Another project with the Name "
									+ project.getName() + " already exists.");
				}

			} catch (SQLException ex) {
			}
			if (ErrorController.get().errorsExist()) {
				ErrorController.get().displayErrors();
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
				// JOptionPane.showMessageDialog(null, ex);// DMITRI (MC
				// shouldn't be taking to the GUI)
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
	public boolean initializeProject(MainController mc, Project project) {
		if (validProject(mc, project)) {
			String sql;
			try {

				sql = "select * from Projects where Name = ?";
				mc.pst = mc.conn.prepareStatement(sql);
				mc.pst.setString(1, project.getName());
				mc.rs = mc.pst.executeQuery();
				while (mc.rs.next()) {
					ErrorController.get().addError(
							"Project with Name \"" + project.getName()
									+ "\" already exists.");
				}
				sql = "select count(*) from Members where MID = ?";
				mc.pst = mc.conn.prepareStatement(sql);
				mc.pst.setInt(1, project.getManagerID());
				mc.rs = mc.pst.executeQuery();
				if (mc.rs.getInt(1) == 0) {
					ErrorController.get().addError(
							"Manager with ID " + project.getManagerID()
									+ " does not exist.");
				}
			} catch (SQLException ex) {
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
				return true;
			} catch (Exception ex) {
				// JOptionPane.showMessageDialog(null, ex);
				mc.ec.addError(ex.getLocalizedMessage());
			}
		}
		return false;
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
					ErrorController.get().addError(
							"Activity with PID " + activity.getProjectID()
									+ " and Number " + activity.getNumber()
									+ " already exists.");
				}
			} catch (SQLException ex) {
			}
			if (mc.ec.errorsExist()) {
				mc.ec.displayErrors();
				return null;
			}
			try {
				// Here we get the max number for the current project, as with 2
				// primary keys we cannot auto-increment them.
				sql = "select Max(number) from Activities where PID = ?";
				mc.pst = mc.conn.prepareStatement(sql);
				mc.pst.setInt(1, mc.getCurrentProject().getProjectID());
				mc.rs = mc.pst.executeQuery();
				int x = mc.rs.getInt(1);
				x++;
				sql = "insert into Activities "
						+ "(PID,Number,Name,Description,PlannedValue,MostLikelyTimeToCompletion,"
						+ "OptimisticTimeToCompletion,PessimisticTimeToCompletion,TargetCompletionDate,Status,ActualCost,PercentComplete)"
						+ "values(?,?,?,?,?,?,?,?,?,?,?,?)";
				mc.pst = mc.conn.prepareStatement(sql);
				mc.pst.setInt(1, mc.getCurrentProject().getProjectID());
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
				activity.setProjectID(mc.getCurrentProject().getProjectID());
				activity.setNumber(x);
				mc.activities.add(activity);
				return activity;
			} catch (Exception ex) {
				// JOptionPane.showMessageDialog(null, ex);// DMITRI (MC
				// shouldn't be taking to the GUI)
			}
		}
		return null;

	}

	/**
	 * @param mc
	 * @param activity
	 * @return
	 */
	public boolean updateActivity(MainController mc, Activity activity) {
		if (!activity.getName().equals("") && activity.getName() != null
				&& !activity.getDescr().equals("")
				&& activity.getDescr() != null) {
			String sql;
			sql = "update Activities set Name=?,Description=?,PlannedValue=?,MostLikelyTimeToCompletion=?,"
					+ "OptimisticTimeToCompletion=?,PessimisticTimeToCompletion=?,TargetCompletionDate=?,Status=?,ActualCost=?,PercentComplete=?"

					+ " where PID = ? and Number = ?";
			try {
				mc.pst = mc.conn.prepareStatement(sql);
				mc.pst.setString(1, activity.getName());
				mc.pst.setString(2, activity.getDescr());
				mc.pst.setDouble(3, activity.getPlannedValue());
				mc.pst.setDouble(4, activity.getMostLikelyTimeToCompletion());
				mc.pst.setDouble(5, activity.getOptimisticTimeToCompletion());
				mc.pst.setDouble(6, activity.getPessimisticTimeToCompletion());
				mc.pst.setDouble(7, activity.getTargetCompletionDate());
				mc.pst.setBoolean(8, activity.getStatus());
				mc.pst.setDouble(9, activity.getActualCost());
				mc.pst.setDouble(10, activity.getPercentComplete());
				mc.pst.setInt(11, activity.getProjectID());
				mc.pst.setInt(12, activity.getNumber());
				mc.pst.execute();
				mc.pst.close();
				// JOptionPane.showMessageDialog(null, "Data saved."); // DMITRI
				// (MC shouldn't be taking to the GUI)
				return true;
			} catch (Exception ex) {
				// JOptionPane.showMessageDialog(null, ex); // DMITRI
			}
		}
		return false;
	}

	/**
	 * @param mc
	 * @param activity
	 * @param activities
	 * @return
	 */
	public boolean createActivityDependencies(MainController mc,
			Activity activity, ArrayList<Activity> activities) {
		String sql;
		try {
			for (Activity dependantActivity : activities) {
				sql = "insert into ActivityDependency(PID,Number,DependantOnPID,DependantOnNumber) values(?,?,?,?)";
				mc.pst = mc.conn.prepareStatement(sql);
				mc.pst.setInt(1, activity.getProjectID());
				mc.pst.setInt(2, activity.getNumber());
				mc.pst.setInt(3, dependantActivity.getProjectID());
				mc.pst.setInt(4, dependantActivity.getNumber());
				mc.pst.execute();
				mc.pst.close();
			}
			return true;
		} catch (SQLException ex) {

		}
		return false;
	}

	/**
	 * @param mc
	 * @param ma
	 * @return
	 */
	public MemberActivity initializeMemberActivity(MainController mc,
			MemberActivity ma) {
		if (ma.getMemberID() > 0 && ma.getProjectID() > 0 && ma.getNumber() > 0) {
			if (ErrorController.get().errorsExist()) {
				ErrorController.get().displayErrors();
				return null;
			}
			String sql = "insert into MemberActivities (MID,PID,Number) values(?,?,?)";
			try {
				mc.pst = mc.conn.prepareStatement(sql);
				mc.pst.setInt(1, ma.getMemberID());
				mc.pst.setInt(2, ma.getProjectID());
				mc.pst.setInt(3, ma.getNumber());
				mc.pst.execute();
				mc.pst.close();
				mc.memberActivities.add(ma);
				return ma;
			} catch (Exception ex) {
				// JOptionPane.showMessageDialog(null, ex);//DMITRI
			}
		}
		return null;
	}
}