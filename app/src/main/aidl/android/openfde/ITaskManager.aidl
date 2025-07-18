package android.openfde;

interface ITaskManager {
    String getTasks();
    void killTaskByPid(int pid);
    String getIconB64ByTaskName(String taskName,int isAndroidApp);
    String getTaskPids();
    String getTaskUpdateInfoByPid(int pid);
    String getEachCPUPercent(int interval);
    String getMemoryAndSwap();
    String getNetworkDownloadAndUpload(int interval);
    String getDiskReadAndWrite(int interval);
    String getFileSystemUsage();
    void changeTaskPriority(int pid, int priority);
    String getUserName();
}