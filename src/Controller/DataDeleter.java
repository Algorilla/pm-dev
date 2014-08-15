/**
 * 
 */
package Controller;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import PModel.Member;
import PModel.Project;

/**
 *
 */
public class DataDeleter {

	/**
	 * @param mc
	 * @param member
	 * @return
	 */
	public boolean deleteMember(MainController mc, Member member) {
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
		sql = "delete from Members where MID = ?";
		try {
			mc.pst = mc.conn.prepareStatement(sql);
			mc.pst.setInt(1, member.getMemberID());
			mc.pst.execute();
			mc.pst.close();
			int x = 0;
			for (Member oldMember : mc.members)
				if (oldMember.getMemberID() == member.getMemberID()) {
					break;
				} else {
					x++;
				}
			mc.members.remove(x);
			return true;
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex);
		}
		return false;
	}

	/**
	 * @param mc
	 * @param project
	 * @return
	 */
	public boolean deleteProject(MainController mc, Project project) {
		String sql;
		try {
			sql = "select count(*) from Projects where Name = ?";
			mc.pst = mc.conn.prepareStatement(sql);
			mc.pst.setString(1, project.getName());
			mc.rs = mc.pst.executeQuery();
			if (mc.rs.getInt(1) == 0) {
				ErrorController.get().AddError(
						"Project with name " + project.getName()
								+ " does not exist.");
			}
		} catch (SQLException ex) {
		}

		if (ErrorController.get().ErrorsExist()) {
			ErrorController.get().DisplayErrors();
			return false;
		}
		sql = "delete from Projects where Name = ?";
		try {
			deleteProjectActivities(mc, project);
			mc.pst = mc.conn.prepareStatement(sql);
			mc.pst.setString(1, project.getName());
			mc.pst.execute();
			mc.pst.close();
			int x = 0;
			for (Project oldProject : mc.projects)
				if (oldProject.getName() == project.getName())
					break;
				else
					x++;
			mc.projects.remove(x);
			return true;
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex);
		}
		return false;
	}
	/**
	 * Deletes all activities associated with a project.
	 * 
	 * @param project
	 *            Project from which to delete all activities
	 * @return Deletion status ( success or fail )
	 */
	// TODO: Where this is done will most likely change as the project
	// progresses.
	// TODO: Where this is done will most likely change as project development
	// goes on
	private boolean deleteProjectActivities(MainController mc, Project project) {
		String sql = "delete from Activities where PID = ?";
		String sql2 = "delete from ActivityDependency where PID = ?";
		String sql3 = "delete from MemberActivities where PID = ?";
		try {
			mc.pst = mc.conn.prepareStatement(sql);
			mc.pst.setInt(1, project.getProjectID());
			mc.pst.execute();
			mc.pst.close();

			mc.pst = mc.conn.prepareStatement(sql2);
			mc.pst.setInt(1, project.getProjectID());
			mc.pst.execute();
			mc.pst.close();

			mc.pst = mc.conn.prepareStatement(sql3);
			mc.pst.setInt(1, project.getProjectID());
			mc.pst.execute();
			mc.pst.close();

			for (int x = 0; x < mc.activities.size(); x++) {
				if (mc.activities.get(x).getProjectID() == project.getProjectID()) {
					mc.activities.remove(x);
					x--;
				}
			}

			return true;
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex);
		}
		return false;
	}

}
