package offshoregroundsampling.db;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.workbench.lifecycle.PostContextCreate;
import org.eclipse.e4.ui.workbench.lifecycle.PreSave;

import offshoregroundsampling.dao.SampleDAO;

public class DatabaseLifecycleManager {

    @PostContextCreate
    void postContextCreate(IEclipseContext context) {
        // Initialize the database connection
        DatabaseConnectionManager.getInstance();
        // Initialize the database schema
        SampleDAO.initialize();
        // Additional initialization tasks
    }
    
    @PreSave
    void preSave() {
        // Close the database connection
    	DatabaseConnectionManager.getInstance().closeConnection();
    }
}

