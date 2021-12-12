package com.example.myapplication_menu.ui.Sucursales;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication_menu.databinding.FragmentSucursalesBinding;

public class Sucursales extends Fragment {

private FragmentSucursalesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            binding = FragmentSucursalesBinding.inflate(inflater, container, false);
            View root = binding.getRoot();

        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}