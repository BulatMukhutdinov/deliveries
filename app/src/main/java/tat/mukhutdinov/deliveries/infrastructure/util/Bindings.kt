package tat.mukhutdinov.deliveries.infrastructure.util

import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import tat.mukhutdinov.deliveries.R
import tat.mukhutdinov.deliveries.infrastructure.model.NetworkState

@BindingAdapter("image")
fun setImage(image: ImageView, url: String?) {
    if (url.isNullOrEmpty()) {
        image.setImageDrawable(ContextCompat.getDrawable(image.context, R.drawable.ic_account_box))
    } else {
        Picasso.get()
            .load(url)
            .placeholder(R.drawable.ic_account_box)
            .into(image)
    }
}

@BindingAdapter("networkError")
fun setNetworkError(text: TextView, state: NetworkState) {
    text.text = when (state) {
        is NetworkState.Error -> state.message.orEmpty()
        NetworkState.Loaded -> ""
        else -> text.text
    }
}