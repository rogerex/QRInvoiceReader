package bo.bumbay.qrinvoicereader;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import bo.bumbay.qrinvoicereader.common.UpdateListener;
import bo.bumbay.qrinvoicereader.entity.Folder;
import bo.bumbay.qrinvoicereader.repository.FileManagerRepository;

public class UpdateFolderDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Rename");
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_folder, null);
        builder.setView(view);

        long id = getArguments().getLong("id");
        final Folder folder = FileManagerRepository.getFolder(id);

        final EditText nameControl = (EditText)view.findViewById(R.id.folder_name);
        nameControl.setText(folder.name);
        nameControl.setSelectAllOnFocus(true);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String name = nameControl.getText().toString();

                folder.name = name;
                FileManagerRepository.save(folder);

                ((UpdateListener)getActivity()).update();
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                UpdateFolderDialogFragment.this.getDialog().cancel();
            }
        });
        return builder.create();
    }
}
