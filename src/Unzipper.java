import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Unzipper {
	//	private String defaultDirectory = new JFileChooser().getFileSystemView().getDefaultDirectory().toString() + File.separator + "Allure Reports";
//	private static String defaultDirectory = "E:"+ File.separator + "Allure Reports";
	private static String defaultDirectory = "C:\\Users\\HOWARS19\\projects\\Allure_Report_Opener"+ File.separator + "Allure Reports";

	/**
	 *Sets a new value for the defaultDirectory
	 * @param directory String of the directory to be save unzipped files
	 */
	public void setDefaultDirectory(String directory){
		defaultDirectory = directory;
	}

	/**
	 * returns the current value of defaultDirectory
	 * @return String value of defaultDirectory
	 */
	public String getDefaultDirectory(){
		return defaultDirectory;
	}

	/**
	 * Takes in a directory of a zip and unzips it,
	 * ZipEntry is also used to provide the metadata about ech file entry
	 *
	 * @param zipFilePath String of a directory to be unzipped
	 * @param destFolder String of destination of unzipped files
	 * @param directoryName String of folder within destination where files are saved
	 */
	public void unzipFile(String zipFilePath, String destFolder, String directoryName) throws IOException {
		try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFilePath))) {
			ZipEntry entry;     //used to provide metadata about each entry (file or directory) within a zip folder
			byte[] buffer = new byte[1024];     //This buffer is employed in Java when working with ZIP files (or any file input/output) to read data in chunks and write it out efficiently
			String entryName;

			entry = zis.getNextEntry();
			File ParentDirectory = new File(destFolder + File.separator + directoryName + File.separator);
			if (entry.isDirectory()) {
				ParentDirectory.mkdirs();
			}

			while ((entry = zis.getNextEntry()) != null) {

				entryName= entry.getName().substring(entry.getName().indexOf("/")+1);
				File newFile = new File(ParentDirectory + File.separator + entryName);

				if (entry.isDirectory()) {
					newFile.mkdirs();
				}
				else {
					new File(newFile.getParent()).mkdirs();
					try (FileOutputStream fos = new FileOutputStream(newFile)) {
						int length;
						while ((length = zis.read(buffer)) > 0) {
							fos.write(buffer, 0, length);
						}
					}
				}
			}
		}
	}
}
