/*
 *  Copyright (c) 2016, Travelyaari and/or its affiliates. All rights reserved.
 *
 *   Redistribution and use in source and binary forms, with or without
 *   modification, are permitted provided that the following conditions
 *   are met:
 *
 *     - Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *
 *     - Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *
 *     - Neither the name of Travelyaari or the names of its
 *       contributors may be used to endorse or promote products derived
 *       from this software without specific prior written permission.
 *
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 *   IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 *   THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 *   PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 *   CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *   EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *   PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 *   PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *   LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *   NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *   SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

package com.travelyaari.tycorelib.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Triode on 4/29/2016.
 * Class contains base adapter for all the recycler view used in the app
 */
public class RecyclerAdapter {


    public static abstract class BaseRecycleViewAdapter<T, VH extends ViewHolder> extends
            RecyclerView.Adapter<VH> implements List<T> {

        /**
         * List holds the data needed for the adapter
         */
        private List<T> mDataprovider;

        /**
         * Constructor function
         */
        public BaseRecycleViewAdapter() {
            super();
            mDataprovider = new ArrayList<T>(0);
        }

        public List<T> getmDataprovider() {
            return mDataprovider;
        }

        public void setmDataprovider(List<T> mDataprovider) {
            this.mDataprovider = mDataprovider;
            notifyDataSetChanged();
        }

        /**
         * Inserts the specified object into this {@code List} at the specified location.
         * The object is inserted before the current element at the specified
         * location. If the location is equal to the size of this {@code List}, the object
         * is added at the end. If the location is smaller than the size of this
         * {@code List}, then all elements beyond the specified location are moved by one
         * position towards the end of the {@code List}.
         *
         * @param location the index at which to insert.
         * @param object   the object to add.
         * @throws UnsupportedOperationException if adding to this {@code List} is not supported.
         * @throws ClassCastException            if the class of the object is inappropriate for this
         *                                       {@code List}.
         * @throws IllegalArgumentException      if the object cannot be added to this {@code List}.
         * @throws IndexOutOfBoundsException     if {@code location < 0 || location > size()}
         */
        @Override
        public void add(int location, T object) {
            mDataprovider.add(location, object);
            notifyItemInserted(location);
        }

        /**
         * Adds the specified object at the end of this {@code List}.
         *
         * @param object the object to add.
         * @return always true.
         * @throws UnsupportedOperationException if adding to this {@code List} is not supported.
         * @throws ClassCastException            if the class of the object is inappropriate for this
         *                                       {@code List}.
         * @throws IllegalArgumentException      if the object cannot be added to this {@code List}.
         */
        @Override
        public boolean add(T object) {
            final boolean result = mDataprovider.add(object);
            if (result) {
                notifyItemInserted(mDataprovider.size() - 2);
            }
            return result;
        }

        /**
         * Inserts the objects in the specified collection at the specified location
         * in this {@code List}. The objects are added in the order they are returned from
         * the collection's iterator.
         *
         * @param location   the index at which to insert.
         * @param collection the collection of objects to be inserted.
         * @return true if this {@code List} has been modified through the insertion, false
         * otherwise (i.e. if the passed collection was empty).
         * @throws UnsupportedOperationException if adding to this {@code List} is not supported.
         * @throws ClassCastException            if the class of an object is inappropriate for this
         *                                       {@code List}.
         * @throws IllegalArgumentException      if an object cannot be added to this {@code List}.
         * @throws IndexOutOfBoundsException     if {@code location < 0 || location > size()}.
         * @throws NullPointerException          if {@code collection} is {@code null}.
         */
        @Override
        public boolean addAll(int location, Collection<? extends T> collection) {
            final boolean result = mDataprovider.addAll(location, collection);
            if (result)
                notifyItemRangeChanged(location, location + collection.size());
            return result;
        }

        /**
         * Adds the objects in the specified collection to the end of this {@code List}. The
         * objects are added in the order in which they are returned from the
         * collection's iterator.
         *
         * @param collection the collection of objects.
         * @return {@code true} if this {@code List} is modified, {@code false} otherwise
         * (i.e. if the passed collection was empty).
         * @throws UnsupportedOperationException if adding to this {@code List} is not supported.
         * @throws ClassCastException            if the class of an object is inappropriate for this
         *                                       {@code List}.
         * @throws IllegalArgumentException      if an object cannot be added to this {@code List}.
         * @throws NullPointerException          if {@code collection} is {@code null}.
         */
        @Override
        public boolean addAll(Collection<? extends T> collection) {
            final int position = mDataprovider.size() - 1;
            final boolean result = mDataprovider.addAll(collection);
            if (result)
                notifyItemRangeChanged(position, position + collection.size());
            return result;
        }

        /**
         * Removes all elements from this {@code List}, leaving it empty.
         *
         * @throws UnsupportedOperationException if removing from this {@code List} is not supported.
         * @see #isEmpty
         * @see #size
         */
        @Override
        public void clear() {
            final int size = getItemCount();
            if (size > 0) {
                mDataprovider.clear();
                notifyItemMoved(0, size - 1);
            }
        }

        /**
         * Tests whether this {@code List} contains the specified object.
         *
         * @param object the object to search for.
         * @return {@code true} if object is an element of this {@code List}, {@code false}
         * otherwise
         */
        @Override
        public boolean contains(Object object) {
            return mDataprovider.contains(object);
        }

        /**
         * Tests whether this {@code List} contains all objects contained in the
         * specified collection.
         *
         * @param collection the collection of objects
         * @return {@code true} if all objects in the specified collection are
         * elements of this {@code List}, {@code false} otherwise.
         * @throws NullPointerException if {@code collection} is {@code null}.
         */
        @Override
        public boolean containsAll(Collection<?> collection) {
            return mDataprovider.containsAll(collection);
        }

        /**
         * Returns the element at the specified location in this {@code List}.
         *
         * @param location the index of the element to return.
         * @return the element at the specified location.
         * @throws IndexOutOfBoundsException if {@code location < 0 || location >= size()}
         */
        @Override
        public T get(int location) {
            return mDataprovider.get(location);
        }

        /**
         * Searches this {@code List} for the specified object and returns the index of the
         * first occurrence.
         *
         * @param object the object to search for.
         * @return the index of the first occurrence of the object or -1 if the
         * object was not found.
         */
        @Override
        public int indexOf(Object object) {
            return mDataprovider.indexOf(object);
        }

        /**
         * Returns whether this {@code List} contains no elements.
         *
         * @return {@code true} if this {@code List} has no elements, {@code false}
         * otherwise.
         * @see #size
         */
        @Override
        public boolean isEmpty() {
            return mDataprovider.isEmpty();
        }

        /**
         * Returns an iterator on the elements of this {@code List}. The elements are
         * iterated in the same order as they occur in the {@code List}.
         *
         * @return an iterator on the elements of this {@code List}.
         * @see Iterator
         */
        @NonNull
        @Override
        public Iterator<T> iterator() {
            return mDataprovider.iterator();
        }

        /**
         * Searches this {@code List} for the specified object and returns the index of the
         * last occurrence.
         *
         * @param object the object to search for.
         * @return the index of the last occurrence of the object, or -1 if the
         * object was not found.
         */
        @Override
        public int lastIndexOf(Object object) {
            return mDataprovider.lastIndexOf(object);
        }

        /**
         * Returns a {@code List} iterator on the elements of this {@code List}. The elements are
         * iterated in the same order that they occur in the {@code List}.
         *
         * @return a {@code List} iterator on the elements of this {@code List}
         * @see ListIterator
         */
        @Override
        public ListIterator<T> listIterator() {
            return mDataprovider.listIterator();
        }

        /**
         * Returns a list iterator on the elements of this {@code List}. The elements are
         * iterated in the same order as they occur in the {@code List}. The iteration
         * starts at the specified location.
         *
         * @param location the index at which to start the iteration.
         * @return a list iterator on the elements of this {@code List}.
         * @throws IndexOutOfBoundsException if {@code location < 0 || location > size()}
         * @see ListIterator
         */
        @NonNull
        @Override
        public ListIterator<T> listIterator(int location) {
            return mDataprovider.listIterator(location);
        }

        /**
         * Removes the object at the specified location from this {@code List}.
         *
         * @param location the index of the object to remove.
         * @return the removed object.
         * @throws UnsupportedOperationException if removing from this {@code List} is not supported.
         * @throws IndexOutOfBoundsException     if {@code location < 0 || location >= size()}
         */
        @Override
        public T remove(int location) {
            final T object = mDataprovider.remove(location);
            if (object != null) {
                notifyItemRemoved(location);
            }
            return object;
        }

        /**
         * Removes the first occurrence of the specified object from this {@code List}.
         *
         * @param object the object to remove.
         * @return true if this {@code List} was modified by this operation, false
         * otherwise.
         * @throws UnsupportedOperationException if removing from this {@code List} is not supported.
         */
        @Override
        public boolean remove(Object object) {
            final int index = indexOf(object);
            final boolean result = mDataprovider.remove(object);
            if (result) {
                notifyItemRemoved(index);
            }
            return result;
        }

        /**
         * Removes all occurrences in this {@code List} of each object in the specified
         * collection.
         *
         * @param collection the collection of objects to remove.
         * @return {@code true} if this {@code List} is modified, {@code false} otherwise.
         * @throws UnsupportedOperationException if removing from this {@code List} is not supported.
         * @throws NullPointerException          if {@code collection} is {@code null}.
         */
        @Override
        public boolean removeAll(Collection<?> collection) {
            final boolean result = mDataprovider.removeAll(collection);
            if (result) {
                notifyDataSetChanged();
            }
            return result;
        }

        /**
         * Removes all objects from this {@code List} that are not contained in the
         * specified collection.
         *
         * @param collection the collection of objects to retain.
         * @return {@code true} if this {@code List} is modified, {@code false} otherwise.
         * @throws UnsupportedOperationException if removing from this {@code List} is not supported.
         * @throws NullPointerException          if {@code collection} is {@code null}.
         */
        @Override
        public boolean retainAll(Collection<?> collection) {
            final boolean result = mDataprovider.retainAll(collection);
            if (result) {
                notifyItemChanged(0, collection);
            }
            return result;
        }

        /**
         * Replaces the element at the specified location in this {@code List} with the
         * specified object. This operation does not change the size of the {@code List}.
         *
         * @param location the index at which to put the specified object.
         * @param object   the object to insert.
         * @return the previous element at the index.
         * @throws UnsupportedOperationException if replacing elements in this {@code List} is not supported.
         * @throws ClassCastException            if the class of an object is inappropriate for this
         *                                       {@code List}.
         * @throws IllegalArgumentException      if an object cannot be added to this {@code List}.
         * @throws IndexOutOfBoundsException     if {@code location < 0 || location >= size()}
         */
        @Override
        public T set(int location, T object) {
            final T previous = mDataprovider.set(location, object);
            if (previous != null) {
                notifyItemChanged(location, object);
            }
            return previous;
        }

        /**
         * Returns the number of elements in this {@code List}.
         *
         * @return the number of elements in this {@code List}.
         */
        @Override
        public int size() {
            return mDataprovider.size();
        }

        /**
         * Returns a {@code List} of the specified portion of this {@code List} from the given start
         * index to the end index minus one. The returned {@code List} is backed by this
         * {@code List} so changes to it are reflected by the other.
         *
         * @param start the index at which to start the sublist.
         * @param end   the index one past the end of the sublist.
         * @return a list of a portion of this {@code List}.
         * @throws IndexOutOfBoundsException if {@code start < 0, start > end} or {@code end >
         *                                   size()}
         */
        @NonNull
        @Override
        public List<T> subList(int start, int end) {
            return mDataprovider.subList(start, end);
        }

        /**
         * Returns an array containing all elements contained in this {@code List}.
         *
         * @return an array of the elements from this {@code List}.
         */
        @NonNull
        @Override
        public Object[] toArray() {
            return mDataprovider.toArray();
        }

        /**
         * Returns an array containing all elements contained in this {@code List}. If the
         * specified array is large enough to hold the elements, the specified array
         * is used, otherwise an array of the same type is created. If the specified
         * array is used and is larger than this {@code List}, the array element following
         * the collection elements is set to null.
         *
         * @param array the array.
         * @return an array of the elements from this {@code List}.
         * @throws ArrayStoreException if the type of an element in this {@code List} cannot be stored
         *                             in the type of the specified array.
         */
        @NonNull
        @Override
        public <T1> T1[] toArray(T1[] array) {
            return mDataprovider.toArray(array);
        }

        /**
         * Returns the total number of items in the data set hold by the adapter.
         *
         * @return The total number of items in this adapter.
         */
        @Override
        public int getItemCount() {
            return (mDataprovider != null) ? mDataprovider.size() : 0;
        }

        /**
         * @see RecyclerView.Adapter#onBindViewHolder(RecyclerView.ViewHolder, int)
         */
        @Override
        public final void onBindViewHolder(VH holder, int position) {
            holder.position = position;
            updateView(holder, get(position));
        }

        /**
         * @see RecyclerView.Adapter#onCreateViewHolder(ViewGroup, int)
         */
        @Override
        public final VH onCreateViewHolder(ViewGroup parent, int viewType) {
            return createView(parent, viewType);
        }


        /**
         * function creates the view holder and returns it
         *
         * @param pareGroup The ViewGroup into which the new View will be added after it is bound to
         *                  an adapter position.
         * @param viewType  The view type of the new View.
         * @return A new ViewHolder that holds a View of the given view type.
         */
        protected abstract VH createView(final ViewGroup pareGroup, final int viewType);

        /**
         * function updates the view with the {@code item}
         *
         * @param viewHolder The ViewHolder which should be updated to represent the contents of the
         *                   item at the given position in the data set.
         * @param item       the data item obtained against the position
         */
        protected abstract void updateView(final VH viewHolder, final T item);


        /**
         * @see RecyclerView.Adapter#getItemId(int)
         */
        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

        protected void onDestroy() {
            mDataprovider = null;
        }
    }


    /**
     * View Holder class needs to be extended in the sub classes lof this adapter
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * holds the position against the view in the ist
         */
        public int position;

        /**
         * Constructor function
         *
         * @param itemView the enclosing view
         */
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

}
