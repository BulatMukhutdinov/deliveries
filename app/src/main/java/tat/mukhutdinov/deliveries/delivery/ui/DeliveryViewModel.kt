package tat.mukhutdinov.deliveries.delivery.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import tat.mukhutdinov.deliveries.R
import tat.mukhutdinov.deliveries.databinding.DeliveryBinding
import tat.mukhutdinov.deliveries.infrastructure.ui.BaseViewModel

class DeliveryViewModel : BaseViewModel<DeliveryBindings, DeliveryBinding>(), DeliveryBindings, OnMapReadyCallback {

    override val layoutId: Int = R.layout.delivery

    override val bindings: DeliveryBindings = this

    private val args: DeliveryViewModelArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.delivery = args.delivery

        viewBinding.image.clipToOutline = true

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val marker = LatLng(args.delivery.lat, args.delivery.lng)

        googleMap.addMarker(
            MarkerOptions()
                .position(marker)
                .title(args.delivery.address)
        )

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 15f))
    }

    override fun onBackClicked() {
        view?.findNavController()?.navigateUp()
    }
}