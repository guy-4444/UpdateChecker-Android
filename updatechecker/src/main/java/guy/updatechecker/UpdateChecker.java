package guy.updatechecker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;

public class UpdateChecker {

    public static void checkForUpdate(Context context) {
        String title = "Update Available";
        String message = "There is update available. We strongly recommend you update to the latest version.";
        String updateButton = "UPDATE";
        String cancelButton = "No, Thanks";
        checkForUpdate(context, title, message, updateButton, cancelButton);
    }

    public static void checkForUpdate(Context context
            , String title
            , String message
            , String updateButton
            , String cancelButton) {
        new UpdateCheckerProcess(context, title, message, updateButton, cancelButton).execute();
    }

    public static class UpdateCheckerProcess extends AsyncTask<Void, String, String> {

        private Context context;
        private String currentVersion;
        private String title = "";
        private String message = "";
        private String updateButton = "";
        private String cancelButton = "";


        public UpdateCheckerProcess(Context context
                , String title
                , String message
                , String updateButton
                , String cancelButton) {
            this.context = context;
            this.title = title;
            this.message = message;
            this.updateButton = updateButton;
            this.cancelButton = cancelButton;

            try {
                Log.d("pttt", "Current version " + currentVersion);
                this.currentVersion = context.getPackageManager().getPackageInfo(getMyPackageName(context), 0).versionName;
                Log.d("pttt", "Current version " + currentVersion);

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected String doInBackground(Void... voids) {


            String newVersion = null;
            try {
                // GetVersionCode
                newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + getMyPackageName(context) + "&hl=it")
                        .timeout(30000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get()
                        .select(".hAyfc .htlgb")
                        .get(7)
                        .ownText();
                return newVersion;
            } catch (Exception e) {
                return newVersion;
            }
        }

        @Override
        protected void onPostExecute(String onlineVersion) {
            super.onPostExecute(onlineVersion);
            boolean isNewUpdateOnStore = false;
            if (onlineVersion != null && !onlineVersion.isEmpty() && currentVersion != null && !currentVersion.isEmpty()) {
                if (isUpdateAvailable(currentVersion, onlineVersion)) {
                    isNewUpdateOnStore = true;
                }
            }

            Log.d("pttt", "Current version " + currentVersion + "   |   playstore version " + onlineVersion);
            if (isNewUpdateOnStore) {
                openDialog(context, title, message, updateButton, cancelButton);
            }

        }
    }

    private static boolean isUpdateAvailable(String versionOld, String versionNew) {
        boolean res = false;

        if (!versionOld.equals("0.0.0.0") && !versionNew.equals("0.0.0.0")) {
            res = (versionCompare(versionOld, versionNew)) < 0;
        }

        return res;
    }

    private static int versionCompare(String versionOld, String versionNew) {
        String[] vals1 = versionOld.split("\\.");
        String[] vals2 = versionNew.split("\\.");

        int i = 0;

        while (i < vals1.length && i < vals2.length && vals1[i].equals(vals2[i])) {
            i++;
        }

        if (i < vals1.length && i < vals2.length) {
            try {
                int diff = Integer.valueOf(vals1[i]).compareTo(Integer.valueOf(vals2[i]));
                return Integer.signum(diff);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        }

        return Integer.signum(vals1.length - vals2.length);
    }

    private static void openDialog(final Context context
            , String title
            , String message
            , String updateButton
            , String cancelButton) {
        AlertDialog.Builder adb;
        try {
            adb = new AlertDialog.Builder(context, 0);
        } catch (Exception ex) {
            // for older android versions
            adb = new AlertDialog.Builder(context);
        }
        adb.setTitle(title);
        adb.setMessage(message);
        adb.setIcon(R.drawable.ic_update_google_play);
        adb.setCancelable(false);
        adb.setPositiveButton(updateButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                try {
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getMyPackageName(context) )));
                } catch (android.content.ActivityNotFoundException anfe) {
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getMyPackageName(context) )));
                } catch (Exception ex) {

                }
            }
        });
        adb.setNegativeButton(cancelButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        try {
            adb.show();
        } catch (Exception ex) {
            // activity closed
            ex.printStackTrace();
        }
    }

    private static String getMyPackageName(Context context) {
            return context.getPackageName();
    }

}
