package com.darwinsys.ant;

import java.io.*;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 * An Ant Task to run Symantec Ghost in disk-to-image mode
 */
public class GhostTask extends Task {
	/** The source drive/directory */
	private String source;
	/** The target drive/directory */
	private String target;
	/** Ghost home directory */
	private String ghostHome = "C:\\program files\\symantec\\ghost";
	/** Ghost program, version, extension, ... */
	private String ghostProg = "ghost.exe";

	/** Set the drive or directory name */
	public void setSource(String source) {
		this.source = source;
	}

	/** Set the target file name */
	public void settarget(String target) {
		this.target = target;
	}

	/** Set the Ghost home directory */
	public void setGhostHome(String GhostHome) {
		this.ghostHome = GhostHome;
	}

	/** Set the Ghost home directory */
	public void setGhostProg(String GhostProg) {
		this.ghostProg = GhostProg;
	}

	/** We should be all set now; run the task */
	public void execute() throws BuildException {
		System.out.println("Ghost is: " + ghostHome + "\\" + "ghost.exe");
		File d = new File(ghostHome);
		if (!d.isDirectory()) {
			throw new BuildException("Ghost home " + ghostHome + " does not exist.");
		}
		if (!d.isDirectory()) {
			throw new BuildException("Ghost home " + ghostHome + " does not contain " + ghostProg);
		}
		File prog = new File(d, "ghost.exe");
		String command = null;
		Process proc = null;
		try { 
			command = prog.getCanonicalPath() + "/d " + source + " " + target;
			System.out.println("Command is: " + command);

			// START IT RUNNING
			proc = Runtime.getRuntime().exec(command);

			// ...
			// XXX Get the stdout and stderr, copy to our output.
			// See other Ant tasks to see best way...
			// ...

			// Wait for it to end...
			proc.waitFor();

		} catch (IOException ex) {
			throw new BuildException("Failure in getCanonicalPath()", ex);
		} catch (InterruptedException ex) {
			throw new BuildException("Unexpected InterruptedException", ex);
		}
		// assert(proc != null);
		int ret = proc.exitValue();
		if (ret != 0) {
			throw new BuildException("Ghost program returned exitStatus " + ret);
		}
		// No news is good news; ant will say "BUILD SUCCESSFUL" if no failure.
	}
}
